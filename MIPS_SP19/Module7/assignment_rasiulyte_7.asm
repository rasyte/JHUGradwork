###################################################################
# Functional Description: A main program
# Calculates Jackpot Probablitites and Odds (Module 7 Assignment)
# EN.605.204.81.SP19 Computer Organization
# Johns Hopkins University 
# Created by: Rasa Rasiulyte 
# Date created: 3/26/2019
###################################################################
.data
	stopped: .asciiz 		"\nProgram completed."	
	promptnumsinlrgpool: .asciiz 	"\nEnter integer representing the large pool of possible numbers: "
	promptnumsfromlrgpool: .asciiz 	"\nEnter integer representing the count of numbers to be selected from the large pool: "	
	promptnumsinsmlpool: .asciiz 	"\nEnter integer representing the size of the second smaller pool of numbers: "
	promptnumsfromsmlpool: .asciiz 	"\nEnter integer representing the count of numbers to be selected from the second pool: "	
	probrestultmsg: .asciiz 	"\n***Probablity for the Jackpot is 1 in: "	
	
	k: .word 0 			#store value for k 
	n: .word 0 			#store value for n	
	denumerator: .word 0 		#store value for denumerator
	numerator: .word 0 		#store value for numerator
	probLarge: .word 0 		#store value for probablity in large pool
	probSmall: .word 0 		#store value for probablity in small pool 
	prob: .word 0			#store value for overall probablity 

.text
.globl main
	main: 
	#LARGE POOL WORK	
		jal 	promptUserLargePool 	#prompt user for large pool requirements
		sw 	$s0, n 			#$s0 - total number in large pool (n)
		sw 	$s1, k 			#$s1 - selected number in large pool (k)
		
		add 	$a0, $zero, $s0 	#store all numbers value in $s0 
		add 	$a1, $zero, $s1 	#store selected numbers value in $s1
		jal 	findNumerator		#calculate numerator for probablity formula
		sw 	$v0, numerator 		#store return in numerator memory address 
				
		lw 	$a0, k			#find denumerator k! ($s0!)
		jal 	findFactorial	
		sw 	$v0, denumerator 	#store return in denumerator memory address
		
		lw 	$a0, numerator
		lw 	$a1, denumerator
		div 	$t0, $a0, $a1 		#calculate probablity for large pool 
		sw 	$t0, probLarge		#store probablity value in probLarge memory address
	
	#SMALL POOL WORK		
		jal 	promptUserSmallPool	#prompt user for small pool requirements
		sw 	$s2, n	 		#$s2 - total number in large pool (n)
		sw 	$s3, k 			#$s3 - selected number in large pool (k)
		
		add 	$a0, $zero, $s2		#store all numbers value in $s2 
		add 	$a1, $zero, $s3		#store all numbers value in $s3 
		jal 	findNumerator		#calculate numerator
		sw 	$v0, numerator 		#store return value in numerator memory address 
		
		lw 	$a0, k			#find denumerator k! ($s2!)	
		jal 	findFactorial	
		sw 	$v0, denumerator 	#store return in denumerator memory address
		
		lw 	$a0, numerator		#calculate probablity for small pool 
		lw 	$a1, denumerator
		div 	$t1, $a0, $a1
		sw 	$t1, probSmall		#store small pool probablity value in probSmall memory address 
		
	#ODDS AND FINAL PROBABLITIES WORK
		lw 	$a0, probLarge		#calculate overall probablity for wining Jackpot 
		lw 	$a1, probSmall
		mul 	$t2, $a0, $a1 	
		sw 	$t2, prob		#store value in prob memory address
		
		li 	$v0, 4			#display Jackpot probability restults 
		la 	$a0, probrestultmsg
		syscall		
		li 	$v0, 1
		lw 	$a0, prob
		syscall
		
	#DONE		
		li 	$v0, 4			#tell OS the work is done here 
		la 	$a0, stopped
		syscall		
		li 	$v0, 10 		#end of program
		syscall
	
###################################################################
# Functional Description: promptUserLargePool($s0, $s1)
# collects and stores values for large pool in $s0 and $s1 registers
###################################################################
promptUserLargePool: 
		li 	$v0, 4			#ask user to iput data for large pool
		la 	$a0, promptnumsinlrgpool
		syscall		
		li 	$v0, 5 	
		syscall	
		add 	$s0, $s0, $v0 		#total number of balls in large pool stored in $s0
		
		li 	$v0, 4
		la 	$a0, promptnumsfromlrgpool
		syscall		
		li 	$v0, 5		
		syscall				
		add 	$s1, $s1, $v0 		#store selected numbers from large pool in $s1
		
		jr 	$ra

###################################################################
# Functional Description: promptUserSmallPool($s2, $s3)
# collects and stores values for large pool in $s2 and $s3 registers
###################################################################	
promptUserSmallPool:
		li 	$v0, 4			#ask user to input date for small pool
		la 	$a0, promptnumsinsmlpool
		syscall		
		li 	$v0, 5	
		syscall	
		add 	$s2, $s2, $v0 		#total number of balls in small pool stored in $s2
		
		li 	$v0, 4
		la 	$a0, promptnumsfromsmlpool
		syscall		
		li 	$v0, 5		
		syscall				
		add 	$s3, $s3, $v0 		#selected number of balls from small pool stored in $s3
		
		jr 	$ra

###################################################################
# Functional Description: findFactorial
# $v0 returns k! value for probablities formula
# $a0: points to k original value 
###################################################################		
findFactorial:
		subu 	$sp, $sp, 8
		sw 	$ra, ($sp) 		#store value of return value in stack
		sw 	$s0, 4($sp)		#4 bytes apart
	
		li 	$v0, 1		  	#basecase
		beq 	$a0, 0, factorialDone 	#recursive call until $a0 is equal to zero, base case 
	
		move 	$s0, $a0
		sub 	$a0, $a0, 1
		jal 	findFactorial
	
		mul 	$v0, $s0, $v0		#when recursing rewinding, this will be executed 
	
	factorialDone: 				#getting values back from the stack 
		lw 	$ra, ($sp) 		#restoring the return address from the stack 
		lw 	$s0, 4($sp) 		#loading value of the local variable from the stack 
		addu 	$sp, $sp, 8  		#because we want to restore the stack we add 8
				
		jr 	$ra 

###################################################################
# Functional Description: findNumerator
# $v0: returns value in numerator to be used in probablities formula 
# $a0: points to n value
# $a1: points to k value
###################################################################
findNumerator:
		li 	$v0, 1 			#set denumerator to 1 to start with
		sub 	$a1 ,$a0, $a1 		#(n-k)
		
		loop: 
			mul 	$v0, $v0, $a0   #numerator = (n)*(n-1)
			addi 	$a0, $a0, -1  	#decrease n by 1 
			bgt 	$a0, $a1, loop 	#loop until n > (n-k)
		jr 	$ra
