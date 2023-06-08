package filter;


public interface Filter {

    // Diese Methode verarbeitet eine Eingabedatei und führt eine Reihe von Operationen darauf aus,
    // die von der spezifischen Filterklasse definiert werden. Das kann das Konvertieren des Dateiformats oder
    // das Verschlüsseln oder Entschlüsseln der Daten in der Datei sein

    // @param   inputFilePath   Der Pfad zur Datei, die verarbeitet werden soll
    // @throws  Exception   Wird geworfen, wenn es ein Problem mit dem Lesen oder Schreiben der Datei gibt,
    //                      oder wenn es ein Problem mit der spezifischen Operation gibt, die von der Filterklasse durchgeführt wird
    // @return  Ein String, der den Pfad zur verarbeiteten Datei zurückgibt
    String process(String inputFilePath) throws Exception;
}
