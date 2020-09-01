# Cypher Notepad  [![Documentaion](https://img.shields.io/badge/documentation-%20is%20ready-brightgreen?style=flat-square)](https://cyphernotepad.com/wiki/#/?coverpage=false)

![GitHub release (latest by date)](https://img.shields.io/github/v/release/Cypher-Notepad/Cypher-Notepad?style=flat-square) ![GitHub Release Date](https://img.shields.io/github/release-date/cypher-notepad/cypher-notepad?style=flat-square) ![GitHub All Releases](https://img.shields.io/github/downloads/cypher-notepad/cypher-notepad/total?color=%23ff69b4&style=flat-square) [![HitCount](http://hits.dwyl.com/Cypher-Notepad/Cypher-Notepad.svg)](http://hits.dwyl.com/Cypher-Notepad/Cypher-Notepad) ![GitHub last commit](https://img.shields.io/github/last-commit/cypher-notepad/cypher-notepad?style=flat-square)

> A Java-based, plain-text (<code>.txt</code>) editor for file encryption.

To see all the information of Cypher Notepad, [See our documentation](https://cyphernotepad.com/wiki/#/?coverpage=false).

## What is Cypher Notepad?

Cypher Notepad is a plain-text editor for those wanting a quick, convenient solution to protecting their usernames and passwords, account numbers, and any information they deem private. It features hybrid RSA/AES encryption with a Windows-similar interface, offering both security and ease-of-use. Even those with little-to-no experience encrypting their files can put security concerns out of mind; Cypher Notepad will do the heavy lifting.

**(TL;DR)**
Cypher Notepad is a text editor for users wanting quick and convenient encryption: 

* Usernames and passwords
* Account numbers 
* Any private information 

It's okay if users have little-to-no experience in encryption; they can focus on their files while this program does all the work. 

A Windows-similar interface provides familiarity for users with more secure features. 

![sc](https://cypher-notepad.github.io/resource/doc_main.gif)

## Why another notepad?
Nowadays there's no shortage of applications which provide encryption, but as new software becomes increasingly features-heavy, simplicity falls to short supply. Cypher Notepad is the essence of an encryption application with none of the bloat, and unlike other programs it's also Java-based — guaranteeing the same interface no matter which OS you use. This belies the fact that for users unsure of the best way to protect their files, Cypher Notepad is an easy and secure solution. 

## How it works?

**Especially, How does this program's encryption work?**

It uses hybrid encryption, both RSA and AES: 

|Algorithm|Mode|Padding|Bits|
|:------:|:---:|:---:|:---:|
|RSA| - |OAEPWithSHA-1AndMGF1Padding|1024
|AES|GCM|NoPadding|256

* When you save a text file, Cypher Notepad encrypts your text using an AES algorithm. 
* It then encrypts the AES secret key using an RSA algorithm. 

Then, your RSA secret key is saved internally. 

![algo](https://cypher-notepad.github.io/resource/algorithm.png) 


