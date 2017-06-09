

;thanks for XZYY's inner-built function assembly code

section .data
intbuffer:
	dq 0
fmtd1:
	db"%lld",0
fmtd2:
	db"%lld ",10,0
fmts1:
	db"%s",0
fmts2:
	db"%s",10,0

section .bss
stringbuffer:
	resb 256

extern scanf
extern printf
extern puts
extern strlen
extern memcpy
extern sscanf
extern sprintf
extern malloc
extern strcmp

section .text
getInt:

	push rbp
	mov rbp,rsp
	mov rax,0
	mov rdi,fmtd1
	mov rsi,intbuffer
	call scanf
	mov rax,[intbuffer]
	mov rsp,rbp
	pop rbp
	ret

printInt:
	push rbp
	mov rbp,rsp
	mov rsi,rdi
	mov rax,0
	mov rdi,fmtd2
	call printf
	mov rsp,rbp
	pop rbp
	ret

Array_size:
	mov rax,[rdi-8]
	ret

print:
	push rbp
	mov rbp,rsp
	mov rax,0
	mov rsi,rdi
	mov rdi,fmts1
	call printf
	mov rsp,rbp
	pop rbp
	ret

println:

	call puts
	ret


transtring:

	push rbp
	mov rbp,rsp
	call strlen
	push rdi
	mov rdi,rax
	push rdi
	add rdi,9
	call malloc
	pop rdi
	mov [rax],rdi
	add rax,8
	mov rdx,rdi
	add rdx,1
	mov rdi,rax
	pop rsi
	sub rsp,8
	push rax
	call memcpy
	pop rax
	mov rsp,rbp
	pop rbp
	ret

getString:

	push rbp
	mov rbp,rsp
	mov rax,0
	mov rdi,fmts1
	mov rsi,stringbuffer
	call scanf
	mov rdi,stringbuffer
	call transtring
	mov rsp,rbp
	pop rbp
	ret

toString:

	push rbp
	mov rbp,rsp
	mov rdx,rdi
	mov rax,0
	mov rdi,stringbuffer
	mov rsi,fmtd1
	call sprintf
	mov rdi,stringbuffer
	call transtring
	mov rsp,rbp
	pop rbp
	ret

String_length:

	mov rax,[rdi-8]
	ret

String_substring:

	push rbp
	mov rbp,rsp
	push rdi
	push rsi
	mov rdi,rdx
	sub rdi,rsi
	add rdi,1
	push rdi
	add rdi,9
	call malloc
	pop rdx
	mov [rax],rdx
	add rax,8
	pop rsi
	pop rdi
	add rsi,rdi
	mov rdi,rax
	push rdx
	push rax
	call memcpy
	pop rax
	pop rdx
	mov qword[rax+rdx],0
	mov rsp,rbp
	pop rbp
	ret

String_parseInt:

	mov rsi,fmtd1
	mov rdx,intbuffer
	mov rax,0
	call sscanf
	mov rax,[intbuffer]
	ret

String_ord:

	mov rax,0
	mov al,byte[rdi+rsi]
	ret

String_add:

	push rbp
	mov rbp,rsp
	push rsi
	mov rsi,rdi
	mov rdi,stringbuffer
	mov rdx,[rsi-8]
	push rdx
	call memcpy
	pop rdi
	pop rsi
	add rdi,stringbuffer
	mov rdx,[rsi-8]
	add rdx,1
	call memcpy
	mov rdi,stringbuffer
	call transtring
	mov rsp,rbp
	pop rbp
	ret

String_lt:

	push rbp
	mov rbp,rsp
	call strcmp
	mov rdi,0
	cmp rax,0
	setl dil
	mov rax,rdi
	mov rsp,rbp
	pop rbp
	ret

String_le:

	push rbp
	mov rbp,rsp
	call strcmp
	mov rdi,0
	cmp rax,0
	setle dil
	mov rax,rdi
	mov rsp,rbp
	pop rbp
	ret

String_gt:

	push rbp
	mov rbp,rsp
	call strcmp
	mov rdi,0
	cmp rax,0
	setg dil
	mov rax,rdi
	mov rsp,rbp
	pop rbp
	ret

String_ge:

	push rbp
	mov rbp,rsp
	call strcmp
	mov rdi,0
	cmp rax,0
	setge dil
	mov rax,rdi
	mov rsp,rbp
	pop rbp
	ret

String_eq:

	push rbp
	mov rbp,rsp
	call strcmp
	mov rdi,0
	cmp rax,0
	sete dil
	mov rax,rdi
	mov rsp,rbp
	pop rbp
	ret

String_ne:

	push rbp
	mov rbp,rsp
	call strcmp
	mov rdi,0
	cmp rax,0
	setne dil
	mov rax,rdi
	mov rsp,rbp
	pop rbp
	ret