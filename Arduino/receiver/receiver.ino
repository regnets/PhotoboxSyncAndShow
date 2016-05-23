#include <multiCameraIrControl.h>
#include <VirtualWire.h>

const byte ledPin =  5;
const byte receiverPin =  7;

const int transferRate = 2000;
const int startupDelay = 2000;
const int shutterDelay = 2000;
const int bounceDelay = 500;

Sony nex5(4);

void setup()
{
  pinMode(6, OUTPUT);
  pinMode(9,
  OUTPUT);
  digitalWrite(6, LOW);
  digitalWrite(9, HIGH);
  delay(startupDelay);
  pinMode(ledPin, OUTPUT);
  Serial.begin(9600);
  Serial.println("Begin");
  vw_set_ptt_inverted(true);
  vw_setup(transferRate);
  vw_set_rx_pin(receiverPin);
  vw_rx_start();
}

void loop()
{
  uint8_t buffer[VW_MAX_MESSAGE_LEN];
  uint8_t bufferLength = VW_MAX_MESSAGE_LEN;

  char shutterCommand[6] = {'C', 'l', 'i', 'c', 'k', '\0'};
  char message[6] = {' ', ' ', ' ', ' ', ' ', '\0'};
  

  if (vw_get_message(buffer, &bufferLength))
  {
    for (int i = 0; i < 5; i++)
    {
      message[i] = (char) buffer[i];
    }
    if (strcmp((const char*) message, (const char*) shutterCommand) == 0) {
      vw_rx_stop();
      digitalWrite(ledPin, HIGH);
      delay(shutterDelay);
      nex5.shutterNow();
      delay(bounceDelay);
      digitalWrite(ledPin, LOW);
      vw_rx_start();
    }
  }
}
