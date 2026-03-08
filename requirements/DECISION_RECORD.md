# Decision Record ER

To be discussed:
- Produkt / Autogramm keine eigenen Entitäten, da diese imho nicht erfassungsrelevant sind (selbst wenn es im Kontext von Veranstaltungen Autogrammstunden gibt)
- Warum ist Book --> customer m:n?
    - Die bookRental Table dient quasi als Historisierungstabelle der Ausleihen - ein Buch kann dementsprechend schon öfter ausgeliehen werden, jedoch mit dem Constraint, dass es eben physisch nicht überlappend ausgeborgt werden kann.
    - Die Bridge Table ist hier stateful
- Location Vorschlag f. Umsetzung
    - Stock, Raum, Gang, Regal, Fach
    - Ordnung der Bücher durch Büchereifachangestellte Person
    - Ev. aber simplifizieren -> aktuell müsste man nach 3NF halt dann drölfzig Tables machen.
- event -> eventType notwendig? Der Typ des Events ist imho eigentlich über bookGenre / bookPublisher derived. Man könnte aber dann im Service Layer eine Logik implementieren, dass wenn eventTyp = "Krimi", dann muss bookGenre a. vorhanden sein und b. auch vom typ "Krimi" sein. 