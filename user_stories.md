# User Storije — Knjižara RWA2026

Ovaj dokument opisuje standardne user storije za sistem online knjižare i prikazuje kako ih trenutni domenski model i implementacija rešavaju.

---

## 1. Katalog i pretraga knjiga

### US-01 — Pregled kataloga
> **Kao** posetilac sajta, **želim** da vidim listu svih dostupnih knjiga, **kako bih** pronašao šta me zanima.

**Kako model rešava:**
- `BookEntity` sadrži sva ključna polja: `title`, `isbn`, `price`, `stockQuantity`, `publicationYear`, `description`.
- `GET /api/v1/books` vraća listu svih knjiga.
- **Ograničenje**: trenutno se knjige čuvaju u in-memory `HashMap` unutar `BookRepository`, a ne u bazi — podaci se gube pri restartovanju aplikacije.

---

### US-02 — Detalji knjige
> **Kao** posetilac sajta, **želim** da vidim detalje konkretne knjige (autor, izdavač, cena, slika, opis), **kako bih** mogao da odlučim da li da je kupim.

**Kako model rešava:**
- `BookEntity` ima relacije prema `AuthorEntity` (ManyToMany), `PublisherEntity` (ManyToOne) i `BookImageEntity` (OneToMany s `isPrimary` zastavicom).
- `GET /api/v1/books/{id}` vraća jednu knjigu.
- **Ograničenje**: trenutni `Book` DTO (in-memory model) ima samo jedno `author` polje tipa String — JPA entitet sa bogatim relacijama još nije povezan sa kontrolerom.

---

### US-03 — Filtriranje po kategoriji
> **Kao** posetilac sajta, **želim** da filtriram knjige po žanru ili kategoriji, **kako bih** brže pronašao knjige koje me zanimaju.

**Kako model rešava:**
- `CategoryEntity` podržava hijerarhijsku strukturu: ima `parent_id` (ManyToOne) i listu `subcategories` (OneToMany), što omogućava stablo kategorija (npr. Nauka → Fizika → Kvantna fizika).
- `BookEntity` ima ManyToMany relaciju prema `CategoryEntity`.
- **Ograničenje**: ne postoje endpoint niti servisna metoda za pretragu knjiga po kategoriji.

---

### US-04 — Pretraga po autoru ili naslovu
> **Kao** posetilac sajta, **želim** da pretražujem knjige po naslovu ili imenu autora, **kako bih** direktno pronašao konkretnu knjigu.

**Kako model rešava:**
- `AuthorEntity` postoji sa `firstName` i `lastName`.
- `BookEntity` ima `title` polje.
- **Ograničenje**: nema definisanih query metoda u repozitorijumu ni endpoint-a za pretragu knjiga — samo se mogu dohvatiti sve knjige ili jedna po ID-u.

---

## 2. Korisnici i nalozi

### US-05 — Registracija
> **Kao** novi korisnik, **želim** da kreiram nalog, **kako bih** mogao da naručujem knjige i pratim narudžbine.

**Kako model rešava:**
- `UserEntity` ima sva neophodna polja: `username` (unique), `email` (unique), `password`, `firstName`, `lastName`, `phone`.
- `UserRepository` nudi `findByUsername` za proveru duplikata.
- **Ograničenje**: `UserService.createUser()` i `POST /api/v1/users` su zakomentarisani — registracija nije implementirana.

---

### US-06 — Prijava na sistem
> **Kao** registrovani korisnik, **želim** da se prijavim sa email-om i lozinkom, **kako bih** pristupao svom nalogu.

**Kako model rešava:**
- `UserEntity` čuva lozinku i email.
- `RoleEntity` i `PermissionEntity` definišu RBAC (role-based access control) model sa relacijom `user → roles → permissions`.
- **Ograničenje**: Spring Security je isključen (`<artifactId>spring-boot-starter-security</artifactId>` zakomentarisan) — autentifikacija i autorizacija nisu aktivne.

---

### US-07 — Upravljanje profilom
> **Kao** prijavljen korisnik, **želim** da ažuriram svoje podatke (ime, email, telefon), **kako bih** imao aktuelne informacije na nalogu.

**Kako model rešava:**
- `PUT /api/v1/users/{id}` sa `@Valid` validacijom prihvata `UserDto` i ažurira korisnika.
- `UserDto` validira email format i format telefonskog broja (custom pattern).
- `UserMapper.toDto()` mapira entitet u DTO za odgovor.
- **Radi u potpunosti** — ovo je jedan od kompletnije implementiranih slučajeva.

---

### US-08 — Upravljanje adresama dostave
> **Kao** korisnik, **želim** da sačuvam više adresa dostave, **kako bih** brže završio kupovinu.

**Kako model rešava:**
- `AddressEntity` ima sva polja (`street`, `city`, `postalCode`, `country`) i vezan je ManyToOne prema `UserEntity`.
- `UserEntity` ima OneToMany listu `addresses`.
- **Ograničenje**: ne postoje servis, repozitorijum ni kontroler za adrese.

---

## 3. Korpa i kupovina

### US-09 — Dodavanje knjige u korpu
> **Kao** korisnik, **želim** da dodam knjigu u korpu, **kako bih** mogao da nastavim pregled i kupim više knjiga odjednom.

**Kako model rešava:**
- `CartEntity` je vezan OneToOne prema `UserEntity` — svaki korisnik ima jednu korpu.
- `CartItemEntity` čuva `quantity` i referencu na `BookEntity`, sa unique constraint-om `(cart_id, book_id)` koji sprečava duplikate.
- **Ograničenje**: ne postoje servis, repozitorijum ni kontroler za korpu.

---

### US-10 — Pregled i izmena korpe
> **Kao** korisnik, **želim** da vidim šta se nalazi u mojoj korpi i da promenim količine ili uklanjam stavke, **kako bih** imao kontrolu pre kupovine.

**Kako model rešava:**
- Model podržava ovaj scenario: `CartItemEntity.quantity` se može ažurirati, stavka se može obrisati.
- **Ograničenje**: nije implementirano — nema servisa ni kontrolera.

---

### US-11 — Kreiranje narudžbine
> **Kao** korisnik, **želim** da potvrdim kupovinu iz korpe i kreiram narudžbinu, **kako bih** primio naručene knjige.

**Kako model rešava:**
- `OrderEntity` čuva `status` (PENDING → CONFIRMED → SHIPPED → DELIVERED / CANCELLED), `totalPrice`, referencu na korisnika i adresu dostave.
- `OrderItemEntity` snima `priceAtPurchase` — cena u trenutku kupovine ostaje nepromenjena čak i ako se kasnija cena knjige promeni.
- `OrderEntity` ima OneToOne prema `PaymentEntity`.
- **Ograničenje**: nije implementirano — nema servisa ni kontrolera za narudžbine.

---

### US-12 — Primena kupona
> **Kao** korisnik, **želim** da unesem kupon kod pri kupovini, **kako bih** ostvario popust.

**Kako model rešava:**
- `CouponEntity` podržava dva tipa popusta (`PERCENTAGE`, `FIXED_AMOUNT`), ograničen vremenski opseg (`validFrom`, `validTo`), limit upotrebe (`usageLimit`, `usageCount`) i zastavicu aktivnosti (`isActive`).
- `OrderEntity` ima ManyToMany prema `CouponEntity` — jedna narudžbina može koristiti više kupona.
- **Ograničenje**: nije implementirano — nema servisa ni kontrolera.

---

## 4. Plaćanje

### US-13 — Odabir načina plaćanja
> **Kao** korisnik, **želim** da izaberem kako ću platiti (kartica, PayPal, pouzećem...), **kako bih** koristio meni najpogodniju metodu.

**Kako model rešava:**
- `PaymentMethod` enum pokriva: `CREDIT_CARD`, `DEBIT_CARD`, `PAYPAL`, `BANK_TRANSFER`, `CASH_ON_DELIVERY`.
- `PaymentEntity` čuva `paymentMethod`, `status` (`PENDING`, `COMPLETED`, `FAILED`, `REFUNDED`), `paymentDate` i `amount`.
- **Ograničenje**: nije implementirano — nema servisa ni kontrolera za plaćanje.

---

## 5. Praćenje narudžbina

### US-14 — Pregled istorije narudžbina
> **Kao** korisnik, **želim** da vidim sve moje prethodne narudžbine i njihov status, **kako bih** pratio isporuke.

**Kako model rešava:**
- `OrderEntity` čuva `status` i `createdAt` timestamp.
- `OrderStatus` enum opisuje ceo životni ciklus: PENDING → CONFIRMED → SHIPPED → DELIVERED.
- **Ograničenje**: nije implementirano — nema servisa ni kontrolera.

---

## 6. Recenzije

### US-15 — Ostavljanje recenzije
> **Kao** korisnik koji je kupio knjigu, **želim** da ostavim ocenu i komentar, **kako bih** pomogao drugima pri odluci o kupovini.

**Kako model rešava:**
- `ReviewEntity` čuva `rating` (Integer), `comment` (TEXT) i `reviewDate`.
- Unique constraint `(user_id, book_id)` sprečava višestruke recenzije istog korisnika za istu knjigu.
- **Ograničenje**: nije implementirano — nema servisa ni kontrolera za recenzije.

---

## 7. Administracija (admin panel)

### US-16 — Upravljanje zalihama
> **Kao** administrator, **želim** da pratim i ažuriram zalihe knjiga, **kako bih** znao koje knjige treba naručiti.

**Kako model rešava:**
- `BookEntity.stockQuantity` prati trenutnu zalihu.
- `InventoryLogEntity` evidentira svaku promenu (`changeAmount`, `reason`, `createdAt`) — omogućava audit log zaliha.
- **Ograničenje**: nije implementirano — nema servisa ni kontrolera za inventar.

---

### US-17 — Dodavanje i uređivanje knjiga
> **Kao** administrator, **želim** da dodajem nove knjige u katalog i ažuriram postojeće, **kako bih** održavao katalog aktuelnim.

**Kako model rešava:**
- `POST /api/v1/books` i `PUT /api/v1/books/{id}` postoje i rade (u-memory).
- **Ograničenje**: koristi in-memory repozitorijum umesto JPA — podaci se ne čuvaju trajno.

---

### US-18 — Upravljanje korisnicima
> **Kao** administrator, **želim** da vidim listu korisnika i upravljam njihovim nalozima, **kako bih** imao kontrolu nad pristupom sistemu.

**Kako model rešava:**
- `GET /api/v1/users` i `DELETE /api/v1/users/{id}` su implementirani.
- `UserRepository` nudi pretragu po username-u, imenu i prezimenu, i ulozi.
- `RoleEntity` i `PermissionEntity` omogućavaju granularno upravljanje pravima.
- **Ograničenje**: Spring Security nije aktivan, pa se prava ne proveravaju.

---

## Rezime stanja implementacije

| User Story | Domenski model | Repozitorijum | Servis | Kontroler | Status |
|---|:---:|:---:|:---:|:---:|---|
| US-01 Pregled kataloga | ✅ | ⚠️ in-memory | ✅ | ✅ | Delimično |
| US-02 Detalji knjige | ✅ | ⚠️ in-memory | ✅ | ✅ | Delimično |
| US-03 Filtriranje po kategoriji | ✅ | ❌ | ❌ | ❌ | Samo model |
| US-04 Pretraga | ✅ | ❌ | ❌ | ❌ | Samo model |
| US-05 Registracija | ✅ | ✅ | ❌ | ❌ | Delimično |
| US-06 Prijava | ✅ | ✅ | ❌ | ❌ | Samo model |
| US-07 Upravljanje profilom | ✅ | ✅ | ✅ | ✅ | **Kompletno** |
| US-08 Adrese dostave | ✅ | ❌ | ❌ | ❌ | Samo model |
| US-09 Dodavanje u korpu | ✅ | ❌ | ❌ | ❌ | Samo model |
| US-10 Pregled korpe | ✅ | ❌ | ❌ | ❌ | Samo model |
| US-11 Kreiranje narudžbine | ✅ | ❌ | ❌ | ❌ | Samo model |
| US-12 Primena kupona | ✅ | ❌ | ❌ | ❌ | Samo model |
| US-13 Plaćanje | ✅ | ❌ | ❌ | ❌ | Samo model |
| US-14 Istorija narudžbina | ✅ | ❌ | ❌ | ❌ | Samo model |
| US-15 Recenzije | ✅ | ❌ | ❌ | ❌ | Samo model |
| US-16 Upravljanje zalihama | ✅ | ❌ | ❌ | ❌ | Samo model |
| US-17 Dodavanje knjiga (admin) | ✅ | ⚠️ in-memory | ✅ | ✅ | Delimično |
| US-18 Upravljanje korisnicima | ✅ | ✅ | ✅ | ✅ | **Kompletno** |

> **Legenda**: ✅ Implementirano &nbsp;|&nbsp; ⚠️ Delimično/privremeno &nbsp;|&nbsp; ❌ Nedostaje
