#include <VirtualWire.h>

const byte buttonDelay = 500;
const byte buttonPin = 8;
const byte ledPin =  13;
const byte transmitterPin =  3;

const int transferRate = 2000;

const  char msg[] = {'C', 'l', 'i', 'c', 'k', '\0'};;

byte buttonState = 0;

void setup() {
  pinMode(4, OUTPUT);
  pinMode(5, OUTPUT);
  digitalWrite(4, HIGH);
  digitalWrite(5, LOW);
  pinMode(ledPin, OUTPUT);
  vw_set_ptt_inverted(true);
  vw_setup(transferRate);
  vw_set_tx_pin(transmitterPin);
}

void loop() {
  buttonState = digitalRead(buttonPin);

  if (buttonState == HIGH) {
    vw_send((uint8_t *)msg, 5);
    vw_wait_tx();
    digitalWrite(ledPin, HIGH);
    delay(buttonDelay);
  }
  else {
    digitalWrite(ledPin, LOW);
  }
}
