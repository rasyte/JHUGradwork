############################################################################### 
# Functional Description: A main program
# Convert Roman Numeral to Arabic number (Module 8 Assignment)
# EN.605.204.81.SP19 Computer Organization
# Johns Hopkins University 
# Created by: Rasa Rasiulyte 
# Date created: 4/2/2019
############################################################################### 
			.data
    romanNum: 		.asciiz 		"IVXLCDM"
    arabicNum: 		.byte 			1, 5, 10, 50, 100, 500, 1000
   
    spaceStr: 		.asciiz 		"\n "   
    setPromptStr: 	.asciiz 		"\nEnter the Roman number ex: CXLIII: "
    userInputStr: 	.asciiz 		"\nUser's input: "    
    outputStr:		.asciiz 		"\nDecimal value: "
    loopStr: 		.asciiz 		"\nEnter 1 to convert another Roman number: "    
    stoppedStr: 	.asciiz 		"\n\nProgram completed.\n"
    errorStr: 		.asciiz 		"\nInvalid input. Try again.\n"   
 
    lword: 		.word 0			#store value for left
    sum: 		.word 0			#store value for sum
    input: 		.word 4			#store value for user's input
    repeat: 		.word 2			#store value for repeat operation
  
			.text
			.globl main

main:
    	li 		 $v0, 4			#prompt input 
    	la		 $a0, setPromptStr
    	syscall
   
    	la 		$a0, input
    	la 		$a1, input
    	li 		$v0, 8
    	syscall
   
    	sw 		$ra, 0($sp)
    	addi 		$sp, $sp, -4
   	jal 		init			#initialization   
   	lw 		$ra, 0($sp)		#stack work 
   	addi 		$sp, $sp, 4
############################################################################### 
# Functional Description: promptUser($t0, $s0)
# collects, stores, shows  values for number conversions 
############################################################################### 
promptUser:
    	la 		$a0, userInputStr	#load values into arguments 
    	li 		$v0, 4
    	syscall
   
    	la 		$a0, input
    	li 		$v0, 4
    	syscall
   
   	la 		$a0, outputStr
    	li 		$v0, 4
   	syscall

    	lw 		$a0, sum
    	li 		$v0,1
   	syscall
   
    	la 		$a0, loopStr
    	li		$v0, 4
    	syscall
    
    	li 		$v0, 5
    	syscall
   
   	sw 		$v0, repeat
    	lw 		$t0, repeat   
    	bne 		$t0, 1, endOfProgram
    	move 		$s0, $zero
    	sw 		$zero, sum
    	sw 		$zero, lword
    	j 		main
############################################################################### 
# Functional Description: init($t2, $t3, $t4)
# initialization function that converts roman num to arabic
############################################################################### 
init:
    	sw 		$a1, 4($sp)
    	addi 		$sp, $sp, -4  		#stack work 
    	la 		$t2, input		#load addresess 
    	la 		$t3, romanNum
    	la 		$t4, arabicNum
############################################################################### 
# Functional Description: loop ($t2)
# loops through to the next character in string, updates stack 
############################################################################### 
loop:
    	lb 		$a0, ($t2)
    	beq 		$a0, 10, sumup
    	beq 		$a0, 1, sumup
    	sw 		$ra, 8($sp)
    	addi 		$sp, $sp, -4
    	jal 		index
    	lw 		$ra, 8($sp)
    	addi 		$sp, $sp, 4
    	addi 		$t2, $t2, 1
    	sw 		$ra, 8($sp)
    	addi 		$sp, $sp, -4
    	jal 		loop
    	lw 		$ra, 8($sp)
    	addi 		$sp, $sp, 4
   ###############################################################################
# Functional Description: index ($t3, $t5)
# takes first char in roman num, checks if valid or no match and updates stack
###############################################################################     
index:
    	lb 		$t5, ($t3)
    	beqz 		$t5, error
    	beq 		$a0, $t5, getValue
    	sw 		$ra, 12($sp)
    	addi 		$sp, $sp, -4
    	jal 		increment
    	lw 		$ra, 12($sp)
    	addi 		$sp, $sp, 4
############################################################################### 
# Functional Description: prep ($s0)
# prepares values for computing sumation
############################################################################### 
prep:
    	lw 		$s0, sum
   	beqz 		$s0, initial
    	sw 		$ra, 20($sp)		#stack work 
    	addi 		$sp, $sp, -4
    	jal 		sumup
    	addi 		$sp, $sp, 4
    	lw 		$ra, 20($sp)
    	jr 		$ra
############################################################################### 
# Functional Description: initial ($s0, $t2, $t3, $t9)
# adds values for initial summation
############################################################################### 
initial:  
    	add 		$s0, $s0, $t9
   	sw 		$s0, sum
    	sw 		$t9, lword
    	la 		$t3, romanNum		#load address of roman numbers into temp 
    	addi 		$t2, $t2, 1
    	jal 		loop

############################################################################### 
# Functional Description: increment ($t3)
# increment if no match 
############################################################################### 
increment:  
    	addi 		$t3, $t3, 1
    	jal 		index


######################################################################################
# Functional Description: getValue ($t3, $t6, $t7, $t8, $t9)
# gets address for roman chars, finds  index fro match and stores arabic value in $t9
######################################################################################    
getValue:
    	la 		$t6, romanNum
    	la 		$t7, arabicNum
    	sub 		$t8, $t3, $t6
	add 		$t7, $t7, $t8
    	lbu 		$t9, ($t7)
    	bgeu 		$t9, 232, firstResult
    	j 		secondResult
######################################################################################
# Functional Description: firstResult ($t5, $t9)
# adjust first value 
######################################################################################     
firstResult:
    	seq 		$a2, $t5, 68
    	mul 		$t9, $t9, $zero
   	beq 		$a2, 1, thirdResult
    	addi 		$t9, $t9, 1000
    	j 		secondResult
######################################################################################
# Functional Description: secondResult
# stack work 
######################################################################################  
secondResult: 
	sw 		$ra, 16($sp)
    	addi 		$sp, $sp, -4
    	jal 		prep
    	addi 		$sp, $sp, 4
    	lw 		$ra, 16($sp)
    	jr 		$ra
######################################################################################
# Functional Description: thirdResult ($t9)
# adjust third value 
######################################################################################       
thirdResult:
    	addi 		$t9, $t9, 500
    	j 		secondResult
######################################################################################
# Functional Description: findSum ($t1,$t3, $t9)
# calculate sum and stack work
######################################################################################     
findSum:
    	addi 		$sp, $sp, 8
    	lw 		$t1, lword
    	sw 		$t9, lword
    	la 		$t3, romanNum
    	bge 		$t1, $t9, add
    	blt 		$t1, $t9, sub
######################################################################################
# Functional Description: add ($s0, $t2, $t3, $t9)
# store summation in $s0 and compute addition
######################################################################################  
add:
    	lw 		$s0, sum
    	add 		$s0, $s0, $t9
    	sw 		$s0, sum
    	la 		$t3, romanNum
    	addi 		$t2, $t2, 1
    	jal 		loop
######################################################################################
# Functional Description: sub ($s0, $t1, $t2, $t3, $t9)
# store summation, multiply and subtract then update summation 
######################################################################################   
sub:
    	lw 		$s0, sum
    	mul 		$t1, $t1, 2
    	sub 		$t9, $t9, $t1
    	add 		$s0, $s0, $t9
    	sw 		$s0, sum
    	la 		$t3, romanNum
    	addi 		$t2, $t2, 1
    	jal 		loop
######################################################################################
# Functional Description: sumup ($s0)
# return sum
######################################################################################      
sumup:  
    	sw 		$s0, sum
    	j 		promptUser
######################################################################################
# Functional Description: error
# handle invalid
######################################################################################    
error:
    	li 		$v0, 4  
    	la 		$a0, errorStr
    	syscall
    	j 		main
######################################################################################
# Functional Description: endOfProgram
# finish program
######################################################################################      
endOfProgram: 
   	li 		$v0, 4 			#tell OS the work is done here 
   	la 		$a0, stoppedStr
    	syscall
    	li 		$v0, 10		 	#end of program 
    	syscall
