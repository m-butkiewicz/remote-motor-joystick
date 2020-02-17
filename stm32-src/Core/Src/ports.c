/*
 * ports.c
 *
 *  Created on: Feb 16, 2020
 *      Author: wexfo
 */

#include "main.h"
#include "ports.h"

void setUserLED(void) { HAL_GPIO_WritePin(LD2_GPIO_Port, LD2_Pin, GPIO_PIN_SET); }
void resetUserLED(void) { HAL_GPIO_WritePin(LD2_GPIO_Port, LD2_Pin, GPIO_PIN_RESET); }
void toggleUserLED(void) { HAL_GPIO_TogglePin(LD2_GPIO_Port, LD2_Pin); }

void setMotorStep(void) { HAL_GPIO_WritePin(STEP_GPIO_Port, STEP_Pin, GPIO_PIN_SET); }
void resetMotorStep(void) { HAL_GPIO_WritePin(STEP_GPIO_Port, STEP_Pin, GPIO_PIN_RESET); }
void toggleMotorStep(void) { HAL_GPIO_TogglePin(STEP_GPIO_Port, STEP_Pin); }
void setMotorDirection(void) { HAL_GPIO_WritePin(DIR_GPIO_Port, DIR_Pin, GPIO_PIN_SET); }
void resetMotorDirection(void) { HAL_GPIO_WritePin(DIR_GPIO_Port, DIR_Pin, GPIO_PIN_RESET); }
void setMotorM0(void) { HAL_GPIO_WritePin(M0_GPIO_Port, M0_Pin, GPIO_PIN_SET); }
void resetMotorM0(void) { HAL_GPIO_WritePin(M0_GPIO_Port, M0_Pin, GPIO_PIN_RESET); }
void setMotorM1(void) { HAL_GPIO_WritePin(M1_GPIO_Port, M1_Pin, GPIO_PIN_SET); }
void resetMotorM1(void) { HAL_GPIO_WritePin(M1_GPIO_Port, M1_Pin, GPIO_PIN_RESET); }
void setMotorM2(void) { HAL_GPIO_WritePin(M2_GPIO_Port, M2_Pin, GPIO_PIN_SET); }
void resetMotorM2(void) { HAL_GPIO_WritePin(M2_GPIO_Port, M2_Pin, GPIO_PIN_RESET); }

void _writeMotorM0(uint8_t m) {
	if (m) setMotorM0();
	else resetMotorM0();
}

void _writeMotorM1(uint8_t m) {
	if (m) setMotorM1();
	else resetMotorM1();
}

void _writeMotorM2(uint8_t m) {
	if (m) setMotorM2();
	else resetMotorM2();
}

void setMotorMode(uint8_t mode) {
	if ((mode < 1) || (mode > 7)) {
		resetMotorM0();
		resetMotorM1();
		resetMotorM2();

		return;
	}

	_writeMotorM0(mode & 0b001);
	_writeMotorM1(mode & 0b010);
	_writeMotorM2(mode & 0b100);
}
