.data
str1: .asciiz "Enter the first integer: "
str2: .asciiz "Enter the second integer: "
str3: .asciiz "The sum is "
newline: .asciiz "\n"

.text       # instructions follow this line
main:   # indicates start of code (first instruction to execute)

addi $v0, $zero, 4
  # adds zero and imm value 4 and stores in 32-bit function result registers

la $a0, str1
  #load system register $a0, with the address of the string to be output
syscall

addi $v0, $zero, 5
   # adds zero and imm value 5 and stores in 32-bit function result registers
syscall
add $s0, $zero, $v0
   #adds 0 and value in $v0 and stores in $s0
addi $v0, $zero, 4
   # adds zero and imm value 4 and stores in 32-bit function result registers
la $a0, str2
   #load system register $a0, with the address of the string to be output
syscall

addi $v0, $zero, 5
   # adds zero and imm value 5 and stores in 32-bit function result registers
syscall
add $s1, $zero, $v0
   #adds 0 and value in $v0 and stores in $s1
add $s2, $s0, $s1
   # adds value in $s0 and value in $s1 and stores in $s2
addi $v0, $zero, 4
   # adds zero and imm value 4 and stores in 32-bit function result registers
la $a0, str3
   # load system register $a0, with the address of the string to be output
syscall

addi $v0, $zero, 1
   # adds zero and imm value 1 and stores in 32-bit function result registers
add $a0, $zero, $s2
   # adds value in $s2 and 0 and stores in system register $a0
syscall

addi $v0, $zero, 4
   # adds zero and imm value 4 and stores in 32-bit function result registers
la $a0, newline
   # load system register $a0, with the address of the string to be output
syscall

addi $v0, $zero, 10
   # adds zero and imm value 10 and stores in 32-bit function result registers
syscall
jr  $ra
   # jump to address contained in $ra