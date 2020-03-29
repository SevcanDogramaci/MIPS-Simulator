// STM32F4 Discovery - Assembly template
// Turns on an LED attached to GPIOD Pin 12
// We need to enable the clock for GPIOD and set up pin 12 as output.

// Start with enabling thumb 32 mode since Cortex-M4 do not work with arm mode
// Unified syntax is used to enable good of the both words...

// Make sure to run arm-none-eabi-objdump.exe -d prj1.elf to check if
// the assembler used proper instructions. (Like ADDS)

.thumb
.syntax unified
//.arch armv7e-m

///////////////////////////////////////////////////////////////////////////////
// Definitions
///////////////////////////////////////////////////////////////////////////////
// Definitions section. Define all the registers and
// constants here for code readability.

// Constants
.equ     LEDDELAY,     1000000
.equ     sayi1,        2
.equ     sayi2,        3
// Register Addresses
// You can find the base addresses for all peripherals from Memory Map section
// RM0090 on page 64. Then the offsets can be found on their relevant sections.

// RCC   base address is 0x40023800
//   AHB1ENR register offset is 0x30
.equ     RCC_AHB1ENR,   0x40023830	 // RCC AHB1 peripheral clock reg (page 180)

// GPIOD base address is 0x40020C00
//   MODER register offset is 0x00
//   ODR   register offset is 0x14
.equ     GPIOD_MODER,   0x40020C00 // GPIOD port mode register (page 281)
.equ     GPIOD_ODR,     0x40020C14 // GPIOD output data register (page 283)

// Start of text section
.section .text
///////////////////////////////////////////////////////////////////////////////
// Vectors
///////////////////////////////////////////////////////////////////////////////
// Vector table start
// Add all other processor specific exceptions/interrupts in order here
	.long    __StackTop                 // Top of the stack. from linker script
	.long    _start +1                  // reset location, +1 for thumb mode

///////////////////////////////////////////////////////////////////////////////
// Main code starts from here
///////////////////////////////////////////////////////////////////////////////

_start:
	// Enable GPIOD Peripheral Clock (bit 3 in AHB1ENR register)
	ldr r6, = RCC_AHB1ENR       // Load peripheral clock reg address to r6
	ldr r5, [r6]                // Read its content to r5
	orr r5, 0x8                 // Set bit 3 to enable GPIOD clock
	str r5, [r6]                // Store result in peripheral clock register

	// Make GPIOD Pin12 as output pin (bits 25:24 in MODER register)
	ldr r6, = GPIOD_MODER       // Load GPIOD MODER register address to r6
	ldr r5, [r6]                // Read its content to r5
	ldr r5, = 0x55540000          // Write 01 to bits 24, 25 for P12
	str r5, [r6]                // Store result in GPIOD MODER register

	// Set GPIOD Pin12 to 1 (bit 12 in ODR register)
	ldr r6, = GPIOD_ODR         // Load GPIOD output data register
/*	ldr r5, [r6]                // Read its content to r5
	orr r5, 0x1000              // write 1 to pin 12
	str r5, [r6]                // Store result in GPIOD output data register
    ldr r8, =LEDDELAY       // LEDDELAY değerini R1'e yükle*/
    ldr r3, =sayi1
    ldr r4, =sayi2
    ldr r1, = sayi2
    ldr r2, = sayi2

/*
      LDR   R0,= #3
      BL    factorial
      bl sayiyaz
      ldr r5 ,= #3

      factorial:
      CMP   R0, #0
      ITT       eq
      MOVEQ R0, #1   // if (R0 == 0)
      MOVEQ PC,LR
      cmp r0, #0
      bne fonk
      bx lr
      fonk:
      MOV   R3, R0
      PUSH  {R3, LR}
      SUB   R0, R0, #1
      BL    factorial
      POP   {R3, LR}
      MUL   R0, R3, R0
      bx lr
        mov r10, 5       // counter = 10, Compute fib(10)
        mov r1, 0         //fib(0) = 0
        mov r2, 1        // fib(1) = 1
        mov r3,0                        // Fibonacchi
   	fib:
		   cmp r3,0
		   bne acaba1_mi
		   mov r3,r8
		   bl sayiyaz
		   acaba1_mi:
		   cmp r3,1
		   bne ex
		   bl sayiyaz
		   ex:
	       add r3, r1, r2     //fib(n) = fib(n-1) + fib(n-2)
	       mov r1, r2         // fib(n-1) = fib(n-2)
	       mov r2, r3         //fib(n-2) = fib(n)
	       subs r10, r10, 1   //R10 = R0 - 1. Suffix 's' sets NZCV flags as well.
	       beq end            //If 'Z' flag is set, branch to 'end'
	       bl sayiyaz
	       bal fib            //Branch always to 'fib'

*/


 // Homework Code starts.
	MOV R0, #1 // fibonacci starts
	MOV R2, #5 // fibonacci ends.

	loop_fib: // loop to display fibonacci numbers from R0 to R2
		PUSH {R0}

		BL fib_rec
		BL sayiyaz // Seven segment display displays the result

		ADD R3, R3, #10 // Seven segment display is switched off for one second.
		BL sayiyaz

		POP {R0}
		ADD R0, R0, #1
		CMP R0, R2
		BLT loop_fib
		B exit



	fib_rec: // R0 for fib_rec(RO) - input, R3 for return R3 = fib_rec(R0 - 1) + fib_rec(R0 - 2) - output
		CMP   R0, #0
      	ITT       eq
      	MOVEQ R3, #0   // if (R0 == 0)
      	MOVEQ PC,LR

      	CMP   R0, #1
     	ITT       eq
     	MOVEQ R3, #1   // if (R0 == 1)
     	MOVEQ PC,LR

     	SUB R0, R0, #1
     	PUSH {R0, LR}

     	BL fib_rec
     	POP {R0, LR}

     	MOV R4, R3 // R4: Temporary register for sum

     	SUB R0, R0, #1
     	PUSH {R0, R4, LR}

     	BL fib_rec
     	POP {R0, R4, LR}

     	ADD R3, R3, R4

     	BX LR

   // Homework code ends.


  /*  cmp r4, r3
    	bgt islem
    	mov r1, r4
    	push {lr}
    	bl sayiyaz
        ldr r2,=sayi2

    	islem:
    	mov r1, r3
    	push {lr}
        bl sayiyaz
        ldr r2,=sayi2
		b exit  */    // Küçük Sayıyı yazdırma işlemi

	Delay:
		LDR R7, =#10000000
		PUSH {R7, LR}

		Loop:
			SUBS R7, R7, #1
			BNE Loop
			POP {R7,LR}
			BX LR

sayiyaz:
	push {lr}
    cmp R3, 0
		bne case_1
		ldr r5, = 0x8000
		str r5, [r6]
	B	case_done
	case_1:
	cmp R3, 1
		bne case_2
		ldr r5, = 0xF200
		str r5, [r6]
	B	case_done
	case_2:
	cmp R3, 2
		bne case_3
		ldr r5, = 0x4800
		str r5, [r6]
	B	case_done
	case_3:
	cmp R3, 3
		bne case_4
		ldr r5, = 0x6000
		str r5, [r6]
	B case_done
	case_4:
 	cmp R3, 4
		bne case_5
		ldr r5,= 0x3200
		str r5, [r6]
		B case_done
	case_5:
	cmp R3, 5
		bne case_6
		ldr r5, = 0x2400
		str r5, [r6]
		B case_done
	case_6:
	cmp R3, 6
		bne case_7
		ldr r5, = 0x400
		str r5, [r6]
		B case_done
 	case_7:
 	cmp R3, 7
		bne case_8
		ldr r5, = 0xF000
		str r5, [r6]
		B case_done
	case_8:
	cmp R3, 8
		bne case_9
		ldr r5, = 0x0000
		str r5, [r6]
		B case_done
	case_9:
	cmp R3, 9
		bne case_other
		ldr r5, = 0x3000
		str r5, [r6]
		B case_done
	case_other:
		ldr r5, = 0x3000
		str r5, [r6]
		B case_done
    case_done:
    BL Delay
    pop {lr}
    bx lr

end:
exit:
ldr r2, = sayi2
/*	orr r5, 0x1000              // write 1 to pin 12
	str r5, [r6]                // Store result in GPIOD output data register

    cmp r8, 0
    	bne Equal
    	and r5, 0x0000              // write 1 to pin 12
	    str r5, [r6]

    Equal: sub r8, r8, #1
	               // Store result in GPIOD output data register*/
