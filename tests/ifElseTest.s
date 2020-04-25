addi $s3, $s3, 0
beq $s3, $s4, true
sub $s0, $s1, $s2
j exit
true: add $s0, $s1, $s2
exit: 