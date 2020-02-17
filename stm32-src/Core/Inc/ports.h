/*
 * ports.h
 *
 *  Created on: Feb 16, 2020
 *      Author: wexfo
 */

#ifndef INC_PORTS_H_
#define INC_PORTS_H_

void setUserLED(void);
void resetUserLED(void);
void toggleUserLED(void);

void setMotorStep(void);
void resetMotorStep(void);
void toggleMotorStep(void);
void setMotorDirection(void);
void resetMotorDirection(void);
void setMotorM0(void);
void resetMotorM0(void);
void setMotorM1(void);
void resetMotorM1(void);
void setMotorM2(void);
void resetMotorM2(void);
void setMotorMode(uint8_t mode);

#endif /* INC_PORTS_H_ */
