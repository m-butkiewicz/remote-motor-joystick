################################################################################
# Automatically-generated file. Do not edit!
################################################################################

-include ../makefile.init

RM := rm -rf

# All of the sources participating in the build are defined here
-include sources.mk
-include Drivers/STM32F0xx_HAL_Driver/Src/subdir.mk
-include Core/Startup/subdir.mk
-include Core/Src/subdir.mk
-include subdir.mk
-include objects.mk

ifneq ($(MAKECMDGOALS),clean)
ifneq ($(strip $(C_DEPS)),)
-include $(C_DEPS)
endif
endif

-include ../makefile.defs

# Add inputs and outputs from these tool invocations to the build variables 
EXECUTABLES += \
Remote\ Motor\ Joystick.elf \

SIZE_OUTPUT += \
default.size.stdout \

OBJDUMP_LIST += \
Remote\ Motor\ Joystick.list \

OBJCOPY_BIN += \
Remote\ Motor\ Joystick.bin \


# All Target
all: Remote\ Motor\ Joystick.elf secondary-outputs

# Tool invocations
Remote\ Motor\ Joystick.elf: $(OBJS) $(USER_OBJS) E:\PROJECTS\DO-CV\remote-motor-joystick\stm32-src\STM32F072RBTX_FLASH.ld
	arm-none-eabi-gcc -o "Remote Motor Joystick.elf" @"objects.list" $(USER_OBJS) $(LIBS) -mcpu=cortex-m0 -T"E:\PROJECTS\DO-CV\remote-motor-joystick\stm32-src\STM32F072RBTX_FLASH.ld" --specs=nosys.specs -Wl,-Map="Remote Motor Joystick.map" -Wl,--gc-sections -static --specs=nano.specs -mfloat-abi=soft -mthumb -Wl,--start-group -lc -lm -Wl,--end-group
	@echo 'Finished building target: $@'
	@echo ' '

default.size.stdout: $(EXECUTABLES)
	arm-none-eabi-size  $(EXECUTABLES)
	@echo 'Finished building: $@'
	@echo ' '

Remote\ Motor\ Joystick.list: $(EXECUTABLES)
	arm-none-eabi-objdump -h -S $(EXECUTABLES) > "Remote Motor Joystick.list"
	@echo 'Finished building: $@'
	@echo ' '

Remote\ Motor\ Joystick.bin: $(EXECUTABLES)
	arm-none-eabi-objcopy  -O binary $(EXECUTABLES) "Remote Motor Joystick.bin"
	@echo 'Finished building: $@'
	@echo ' '

# Other Targets
clean:
	-$(RM) *
	-@echo ' '

secondary-outputs: $(SIZE_OUTPUT) $(OBJDUMP_LIST) $(OBJCOPY_BIN)

.PHONY: all clean dependents
.SECONDARY:

-include ../makefile.targets
