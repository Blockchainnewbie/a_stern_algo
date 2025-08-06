
Die **Heuristik** ist wie eine **besondere Zauberkarte** oder ein **"Riecher"**, den unser A*-Schatzsucher hat. Sie hilft ihm, **grob abzuschätzen**, wie weit es **noch bis zum Schatz** ist, wenn er an einem bestimmten Ort steht.

Hier sind die wichtigsten Dinge, die du über diese Zauberkarte wissen musst:

- **Was ist sie genau?**
    
    - Die Heuristik ist eine **Schätzfunktion** oder eine **Daumenregel**. Sie gibt für jeden Ort an, wie weit der Schatz ungefähr entfernt ist.
    - Denk an die **Luftlinie**: Wenn du auf deiner Karte von einem Ort zum Schatz schaust, ist die Luftlinie (der direkte Weg wie ein Vogel fliegt) oft die kürzeste Strecke. Der A*-Algorithmus kann das als seine "Heuristik" benutzen.
    
- **Woher kommt sie?**
    
    - Für eine Straßenkarte, wie die, die wir hatten, kann die Luftlinie berechnet werden, wenn man die Koordinaten der Orte kennt. Das ist wie bei einem echten Navigationssystem: Es hat die Koordinaten aller Orte gespeichert.
    - Um die "Kosten" (z.B. Zeit in Minuten) der Luftlinie zu schätzen, kann der A*-Algorithmus die Luftlinie durch die schnellste Geschwindigkeit teilen, die es auf der ganzen Karte gibt.
    - Es gibt aber auch andere Zaubertricks für andere Schatzsuchen: Wenn du zum Beispiel ein Puzzle lösen willst, bei dem du Bilder verschiebst, könnte der Zaubertrick sein, wie viele Bilder noch an der falschen Stelle liegen.

- **Was kann sie und wofür ist sie gut?**
    
    - **Sie macht die Suche schlauer und schneller!** Unser A*-Schatzsucher ist nicht blind wie andere Sucher (wie der Dijkstra-Algorithmus, der alle Wege gleichmäßig absucht). Die Heuristik sorgt dafür, dass er die Orte zuerst genauer anschaut, die **grob in die richtige Richtung zum Schatz** liegen. Dadurch muss er weniger unnötige Wege untersuchen und findet den Schatz oft viel schneller.
    
    - **Ganz wichtig: Sie darf niemals die tatsächlichen Kosten überschätzen!** Das ist der wichtigste Zauberregel! Wenn der echte Weg zum Schatz 10 Minuten dauert, dann darf deine Zauberkarte höchstens 10 Minuten anzeigen, aber niemals 11 Minuten. Warum? Wenn sie zu viel zeigen würde, würde unser Schatzsucher vielleicht einen viel schnelleren Weg links liegen lassen, weil er denkt, der sei zu lang, und stattdesssen einen anderen, schlechteren Weg wählen. Das würde dazu führen, dass er nicht den wirklich **schnellsten Weg** findet. Die Luftlinie ist hier toll, weil der echte Weg (mit Kurven und Hindernissen) niemals kürzer ist als die direkte Linie.
    
    - Manchmal ist die Heuristik sogar "monoton" oder "konsistent". Das ist ein noch stärkerer Zaubertrick, der dafür sorgt, dass unser Schatzsucher ganz sicher ist, dass er den kürzesten Weg gefunden hat, sobald er einen Ort aus seiner Liste holt. Die Luftlinie ist oft so ein monotoner Zaubertrick.

- **Wann nimmt man sie?**
    
    - Du benutzt die Heuristik, wenn du ein **klares Ziel** hast und **ungefähr weißt, wo es liegt**.
    
    - Sie ist super für Navigationssysteme oder in Computerspielen, wo du einen Weg von A nach B finden willst.
    
	- Wenn du aber gar nicht genau weißt, wo dein Ziel ist – zum Beispiel, wenn du einfach die nächste Tankstelle finden willst, aber nicht weißt, wo die ist – dann kannst du keine Heuristik benutzen. In so einem Fall wäre ein anderer Schatzsucher (wie der Dijkstra-Algorithmus) vielleicht besser. Der Dijkstra-Algorithmus ist sogar wie ein A*-Algorithmus, bei dem die Heuristik immer Null ist, also gar nichts schätzt.

Die Heuristik ist also der Grund, warum der A*-Algorithmus so gut darin ist, schnell den besten Weg zu finden, weil er nicht blind herumirrt, sondern zielgerichtet sucht.