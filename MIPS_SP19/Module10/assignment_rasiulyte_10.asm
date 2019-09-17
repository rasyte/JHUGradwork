.data
	mdArray: .word 1,2,3,4,5,6,7,8,9,10,11,12,13,14,15 #int same number of rows and colloms rows
		 .word 1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21 #colums
		 
	sizeX: 	.word 15  #size for rows and columsn 2x2 matric
	sizeY:  .word 21 #(uppser2)
	lower2: .word 1
	
	J: .word 4
	
	.eqv DATA_SIZE 4  #constant for data size, since it is word the size is 4 (W)
		
.text

	main: 
		la $a0, mdArray #register a0 has base address of mdArray
		lw $a1, sizeX #a1 has a size 
		lw $a2, sizeY 
		lw $a3, lower2
		add $t0, $zero, $a3
		add $t1, $zero, $a1
		sumLoop:
			bgt $t0, $t1, exit			
			# X[I, 2*J+1] := Y[I, 2*J]
			
			#print I
			li 	$v0, 1
			la $a0, ($t0)
			syscall
				
			#increment loop index 
			addi $t0, $t0, 1
			#blt $t0, $a1, sumLoop #if i index is less than size, loop again 
			j sumLoop
			
		#end program 
		exit: 
		li $v0, 10
		syscall
		 
		
	
	
	
