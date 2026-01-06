# BookBuddy 
— Proiect Java Web (Spring Boot)

## 1. Domeniul ales
BookBuddy este un sistem simplu de tip bibliotecă, care gestionează cărți, exemplare (copii) ale cărților și împrumuturi (împrumut/returnare) pentru utilizatori.

---

## 2. Cerințe de business (10)
1. Sistemul trebuie să permită adăugarea unei cărți cu: titlu, ISBN, descriere, autor și categorie.
2. Sistemul trebuie să permită listarea tuturor cărților existente.
3. Sistemul trebuie să permită stocarea autorilor în sistem.
4. Sistemul trebuie să permită stocarea categoriilor în sistem.
5. Sistemul trebuie să permită adăugarea de exemplare (copii) pentru o carte.
6. Sistemul trebuie să rețină starea unui exemplar: disponibil / indisponibil.
7. Sistemul trebuie să permită crearea unui împrumut pentru un exemplar disponibil.
8. Sistemul NU trebuie să permită împrumutarea unui exemplar indisponibil.
9. Sistemul trebuie să permită returnarea unui împrumut și marcarea exemplarului ca disponibil din nou.
10. Sistemul trebuie să rețină utilizatorii (cititorii) cu email unic.

---

## 3. Funcționalități MVP (5)
1. Adăugare carte (Create Book)
2. Listare cărți (List Books)
3. Adăugare exemplar la o carte (Add Copy to Book)
4. Împrumut exemplar (Create Loan / Borrow)
5. Returnare împrumut (Return Loan)

---

## 4. Notă
În etapa MVP se vor implementa endpoint-uri REST pentru cele 5 funcționalități de mai sus, cu persistență în baza de date și testare pentru endpoint-uri și servicii.
