#include <stdio.h>
#include <stdlib.h>

void setup() {
  Serial.begin(9600);
  Serial.println("test");

  execute_with_output("ping www.google.com -c 2");
  
  Serial.println("Enabling BT");
  enable_bluetooth();
  Serial.println("rfkill list");
  execute_with_output("rfkill list");
  Serial.println("bluetoothctl");
  enter_bluetoothctl();
  
}

void loop() {
}

void execute_with_output(const char* cmd) {
  //See: https://stackoverflow.com/questions/646241/c-run-a-system-command-and-get-output
  FILE *fp;
  char path[1035];
  
  /* Open the command for reading. */
  fp = popen(cmd, "r");
  if (fp == NULL) {
    Serial.println("Failed to run command\n" );
    exit(1);
  }

  /* Read the output a line at a time - output it. */
  while (fgets(path, sizeof(path)-1, fp) != NULL) {
    Serial.println(path);
  }

  /* close */
  pclose(fp);  
}

void enable_bluetooth() {
   system("rfkill unblock bluetooth");
}

void enter_bluetoothctl() {
   execute_with_output("bluetoothctl");
}

