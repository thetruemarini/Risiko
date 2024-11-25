PImage mappa; // Variabile per l'immagine della mappa
ArrayList<Territorio> territori = new ArrayList<Territorio>(); // Lista dei territori

void setup() {
  size(1024, 768); // Dimensioni della finestra
  mappa = loadImage("Mappa_Risiko.jpg"); // Carica l'immagine della mappa
  
  // Aggiungi un territorio per l'Alaska
  territori.add(new Territorio(new float[][]{
    {100, 200}, {120, 240}, {140, 250}, {160, 280}, {180, 260}, {200, 270}, // Esempio di punti per l'Alaska
    {220, 290}, {240, 280}, {260, 270}, {280, 250}, {300, 230}, {320, 220},
    {340, 210}, {350, 190}, {370, 180}, {380, 160}, {400, 140} // Continua con i confini
  }));
}

void draw() {
  background(255); // Imposta uno sfondo bianco
  image(mappa, 0, 0, width, height); // Disegna la mappa adattata alla finestra
  
  // Disegna tutti i territori
  for (Territorio t : territori) {
    t.disegna(); // Disegna ogni territorio
  }
}

class Territorio {
  float[][] punti; // Array di punti per il territorio
  boolean illuminato = false; // Se il territorio è illuminato o no
  
  Territorio(float[][] punti) {
    this.punti = punti;
  }
  
  // Disegna il territorio
  void disegna() {
    beginShape(); // Inizia il disegno del poligono
    for (int i = 0; i < punti.length; i++) {
      float x = punti[i][0];
      float y = punti[i][1];
      vertex(x, y); // Aggiungi ogni punto come vertice del poligono
    }
    endShape(CLOSE); // Chiude il poligono
    
    if (illuminato) {
      fill(255, 255, 0, 150); // Colore giallo trasparente per l'illuminazione
    } else {
      fill(255, 0, 0, 150); // Colore rosso trasparente per il territorio non illuminato
    }
    noStroke(); // Nessun bordo
  }
}
