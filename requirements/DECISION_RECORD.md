# Decision Record ER

To be discussed:
- Produkt / Autogramm keine eigenen Entitäten, da diese imho nicht erfassungsrelevant sind (selbst wenn es im Kontext von Veranstaltungen Autogrammstunden gibt)
- Warum Buch -> event als (0,n) -> (0,m)?
    - Ein Buch wird entweder auf keinem Event oder bei beliebigen Events gelesen
    - Ein Event liest entweder 0 Bücher oder beliebig viele Bücher vor (bei Autogrammstunden werden ja beispw. keine Bücher gelesen)
- Warum ist Book --> customer m:n?
    - Die loan Table dient quasi als Historisierungstabelle der Ausleihen - ein Buch kann dementsprechend schon öfter ausgeliehen werden, jedoch mit dem Constraint, dass es eben physisch nicht überlappend ausgeborgt werden kann.
- Location Vorschlag f. Umsetzung
    - Stock, Raum, Gang, Regal, Fach
    - Ordnung der Bücher durch Büchereifachangestellte Person
    - Ev. aber simplifizieren -> aktuell müsste man nach 3NF halt dann drölfzig Tables machen.
- event -> eventType notwendig? Der Typ des Events ist imho eigentlich über bookGenre / bookPublisher derived. Man könnte aber dann im Service Layer eine Logik implementieren, dass wenn eventTyp = "Krimi", dann muss bookGenre a. vorhanden sein und b. auch vom typ "Krimi" sein. 