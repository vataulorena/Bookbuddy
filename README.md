# BookBuddy 
— Proiect Java Web (Spring Boot)

## 1. Descreiere generala
BookBuddy este o aplicație web dezvoltată în Java (Spring Boot) care gestionează o bibliotecă: utilizatori, autori, categorii, cărți, exemplare fizice și împrumuturi.
Aplicația expune un API REST, documentat cu Swagger, utilizează H2 Database pentru persistență și conține teste unitare pentru toate serviciile și endpoint-urile REST.

---

## 2. Cerințe de business (10)
1. Sistemul trebuie să permită adăugarea unei cărți cu următoarele informații: titlu, ISBN, descriere, autor și categorie.
2. Sistemul trebuie să permită listarea tuturor cărților existente în sistem.
3. Sistemul trebuie să permită stocarea și gestionarea autorilor.
4. Sistemul trebuie să permită stocarea și gestionarea categoriilor de cărți.
5. Sistemul trebuie să permită adăugarea de exemplare (copii fizice) pentru o carte.
6. Sistemul trebuie să rețină starea fiecărui exemplar: disponibil sau indisponibil.
7. Sistemul trebuie să permită crearea unui împrumut doar pentru un exemplar disponibil.
8. Sistemul nu trebuie să permită împrumutarea unui exemplar indisponibil.
9. Sistemul trebuie să permită returnarea unui împrumut și marcarea automată a exemplarului ca disponibil.
10. Sistemul trebuie să permită gestionarea utilizatorilor (cititorilor), fiecare utilizator având o adresă de email unică.

---

## 3. Funcționalități MVP (5)
1. Gestionarea utilizatorilor
    - creare utilizatori
    - listare utilizatori cu împrumuturile asociate
2. Gestionarea catalogului de cărți
    - creare autori
    - creare categorii
    - creare cărți
    - listare cărți
3. Gestionarea exemplarelor
    - adăugare exemplar pentru o carte
    - evidența disponibilității
4. Împrumutarea cărților
    - creare împrumut
    - validare disponibilitate exemplar
5. Returnarea cărților
    - returnarea unui împrumut
    - actualizarea automată a disponibilității exemplarului

---

## 4. Structura bazei de date și relații
1. Entități principale
    - User
    - Author
    - Category
    - Book
    - BookCopy
    - Loan

2. Relații între entități
      Author 1 → N Book
      Category 1 → N Book
      Book 1 → N BookCopy
      User 1 → N Loan
      BookCopy 1 → N Loan

---

## 5. Ghid de utilizare a aplicației

Pasul 1: Pornirea aplicației

    - Pornește aplicația rulând clasa BookbuddyApplication
    - Deschide browser-ul și accesează:
            http://localhost:8081/swagger-ui/index.html
    - Swagger UI va afișa toate endpoint-urile disponibile.

Pasul 2: Crearea datelor de bază
    - Pentru a putea crea cărți și împrumuturi, este necesar să existe autori, categorii și utilizatori.
  
  2.1 Creare autor
    Endpoint: POST /api/authors
    Body:
    ```json
    {
      "name": "George Orwell"
    }
    ```
    - Reține id-ul autorului returnat.

  2.2 Creare categorie
    Endpoint: POST /api/categories
    Body:
    ```json
  {
    "name": "Distopie"
  }
    ```
  - Reține id-ul categoriei.

  2.3 Creare utilizator
    Endpoint: POST /api/users
    Body:
    ```json
  {
    "name": "Ana Pop",
    "email": "ana@example.com"
  }
    ```
  - Reține id-ul utilizatorului.


Pasul 3: Gestionarea cărților
  3.1 Creare carte
    Endpoint: POST /api/books
    Body:
    ```json
  {
    "title": "1984",
    "isbn": "9780451524935",
    "description": "Roman distopic",
    "authorId": 1,
    "categoryId": 1
  }
    ```
  - Cartea este salvată în sistem.

  3.2 Listare cărți
    Endpoint: GET /api/books
      - Returnează lista titlurilor de cărți existente.


Pasul 4: Gestionarea exemplarelor
  4.1 Adăugare exemplar pentru o carte
    Endpoint: POST /api/books/{bookId}/copies
    Exemplu:
```json
{
  "available": true
}
```
  - Exemplarul este marcat ca disponibil.


Pasul 5: Împrumutarea unei cărți
  5.1 Creare împrumut
    Endpoint: POST /api/loans
    Body:
```json
{
  "userId": 1,
  "bookCopyId": 1,
  "dueDate": "2026-01-20"
}
```
  - Împrumutul este creat
  - Exemplarul devine indisponibil automat


Pasul 6: Vizualizarea utilizatorilor și a împrumuturilor

  Endpoint: GET /api/users
  Returnează:
    - utilizatorii
    - lista de împrumuturi asociate fiecăruia


Pasul 7: Returnarea unei cărți
  7.1 Returnare împrumut
    Endpoint: POST /api/loans/{loanId}/return
      - imprumutul este închis
      - exemplarul devine din nou disponibil
