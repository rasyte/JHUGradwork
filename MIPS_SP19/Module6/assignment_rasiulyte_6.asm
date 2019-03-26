 	.globl  main 
main:                           # sum of integers and interger squares from 1 to 100 
 
 	.text  
 	add  $t0, $zero, $zero  # I is zero
 	add $s4, $zero, $zero   # Sum is zero  
 	addi $t1, $zero, 100    # set the limit value (100) 
  
loop:  
	addi $t0, $t0, 1        # I = I + 1  
	add $s4, $s4, $t0       # Sum of integers = Sum + I
	mul $t3, $t0, $t0       # Square = I * I
	add $s5, $s5, $t3       # Sum of squares  = Sum + Square 
	blt  $t0, $t1, loop     # I < 100 loop to do again 
 
 	addi $v0, $zero, 4      # print string  str
 	la   $a0, str           # the text for output  
 	syscall                 # call opsys 
 
 	addi $v0, $zero, 1      # print sum of squares  
 	add  $a0, $zero, $s4    # the integer is Sum  
 	syscall                 # call opsys 
 	
 	addi $v0, $zero, 4      # print string  str
 	la   $a0, strsqrs       # the text for output  
 	syscall                 # call opsys 
 	
 	addi $v0, $zero, 1      # print integer  
 	add  $a0, $zero, $s5    # the integer is Sum  
 	syscall                 # call opsys 
 
 	addi $v0, $zero, 4      # print string  
 	la   $a0, stopped       # the text for output  
 	syscall                 # call opsys 
 
 	addi $v0, $zero, 10     # finished .. stop .. return 
  	syscall                 # to the Operating System 
 
	.data 
 str:   
 	.asciiz "\nThe sum of the integers 1 ... 100 is  " 
 strsqrs:
 	.asciiz "\nThe sum of the integer squares 1 ... 100 is  " 

 stopped:  
 	.asciiz "\nStopped." 