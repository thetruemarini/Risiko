PImage mappa; // Variabile per l'immagine della mappa
int territorioColor = color(255, 0, 0); // Colore iniziale per un territorio

void setup() {
  size(1024, 768); // Dimensioni della finestra
  mappa = loadImage("Mappa_Risiko.jpg"); // Carica l'immagine della mappa
}

void draw() {
  background(255); // Imposta uno sfondo bianco
  image(mappa, 0, 0, width, height); // Disegna la mappa a dimensioni adattate alla finestra
  
  // Esempio di un "territorio" come cerchio per testare l'interattività
  fill(territorioColor); // Colore del territorio
  noStroke(); // Nessun bordo
  ellipse(300, 400, 100, 100); // Disegna un cerchio in una posizione della mappa
}

void mousePressed() {
  // Controlliamo se il mouse è stato cliccato sopra il territorio
  float distance = dist(mouseX, mouseY, 300, 400);
  if (distance < 50) { // Se il click è dentro il cerchio (raggio 50)
    territorioColor = color(random(255), random(255), random(255)); // Cambia colore a caso
  }
}
