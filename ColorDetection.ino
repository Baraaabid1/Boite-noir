#include <SoftwareSerial.h>
#define S0 4
#define S1 5
#define S2 6
#define S3 7
#define sensorOut 8
SoftwareSerial espSerial(2, 3); // RX , TX  de la carte esp8266

int frequency_R = 0;

int frequency_G = 0;

int frequency_B = 0;


void setup() {
  pinMode(S0, OUTPUT);
  pinMode(S1, OUTPUT);
  pinMode(S2, OUTPUT);
  pinMode(S3, OUTPUT);
  pinMode(sensorOut, INPUT);
  
  // Setting frequency-scaling to 20%
  digitalWrite(S0,HIGH);
  digitalWrite(S1,LOW);
  
  //Serial.begin(9600);
  Serial.begin(115200);
  espSerial.begin(115200);
  delay(2000);

}

void loop() {
  // Setting red filtered photodiodes to be read
  digitalWrite(S2,LOW);
  digitalWrite(S3,LOW);
  // Reading the output frequency
  frequency_R = pulseIn(sensorOut, LOW);
  // Printing the value on the serial monitor
  Serial.print("R= ");//printing name
  Serial.print(frequency_R);//printing RED color frequency
  Serial.print("  ");
  delay(100);

  // Setting Green filtered photodiodes to be read
  digitalWrite(S2,HIGH);
  digitalWrite(S3,HIGH);
  // Reading the output frequency
  frequency_G = pulseIn(sensorOut, LOW);
  // Printing the value on the serial monitor
  Serial.print("G= ");//printing name
  Serial.print(frequency_G);//printing green color frequency
  Serial.print("  ");
  delay(100);

  // Setting Blue filtered photodiodes to be read
  digitalWrite(S2,LOW);
  digitalWrite(S3,HIGH);
  // Reading the output frequency
  frequency_B = pulseIn(sensorOut, LOW);
  // Printing the value on the serial monitor
  Serial.print("B= ");//printing name
  Serial.print(frequency_B);//printing blue color frequency
  Serial.println("  ");
  delay(100);
  char red = frequency_R;
  char green = frequency_G;
  char blue = frequency_B;
  char input = red+green+blue;
  espSerial.write(input);
 delay(1000);

}