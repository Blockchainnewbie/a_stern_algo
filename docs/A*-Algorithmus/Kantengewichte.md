
Stell dir vor, auf unserer **Schatzkarte** haben wir nicht nur Orte (die Kreise) und Straßen (die Linien), sondern auf jeder Straße steht auch eine **kleine Zahl**.

Diese **Zahlen auf den Straßen** nennen wir "Kantengewichte". Sie sagen uns, wie **"teuer"** oder **wie viel Mühe** es kostet, genau diese eine Straße entlangzugehen, um von einem Ort zum nächsten zu kommen.

Hier sind ein paar Beispiele, was diese Zahlen bedeuten könnten, je nachdem, was für einen Schatz wir suchen:

- Wenn wir den **schnellsten Weg** suchen, könnte die Zahl auf der Straße sagen, wie **viele Minuten** du brauchst, um diese Straße zu fahren. Eine kurze Straße kann manchmal mehr Minuten kosten als eine lange, wenn es zum Beispiel ein holpriger Feldweg ist und die lange Straße eine schnelle Autobahn.
- Wenn wir den **kürzesten Weg** suchen, könnte die Zahl einfach die **Kilometer** der Straße sein.
- Manchmal könnte die Zahl auch verraten, wie viel **Maut** (also Geld) du auf einer Straße bezahlen musst.

**Ganz wichtig ist:** Diese Zahlen sind **immer positiv**! Das bedeutet, du musst immer etwas dafür tun oder ausgeben (Minuten, Kilometer, Geld), um über eine Straße zu kommen. Du bekommst nie Zeit oder Geld zurück, wenn du eine Straße benutzt.

Unser schlauer A*-Schatzsucher benutzt diese Zahlen dann, um die **Gesamtmühe** (oder die Gesamtminuten oder Gesamtkilometer) zu berechnen, die er schon geschafft hat, um zu einem Ort zu gelangen. Das ist der "Weg, den du schon geschafft hast" (`g(x)`), den wir vorhin besprochen haben. Er **zählt einfach alle Zahlen auf den Straßen zusammen**, die er auf seinem Weg schon benutzt hat.