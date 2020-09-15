<p align="center">
  <a href="https://Cypher-Notepad.github.io/"><img height="200" style="height:200px;" src="https://cypher-notepad.github.io/resource/icon.ico" alt="Cypher Notepad"></a>
  <h1 align="center">Cypher Notepad</h1>
</p>
<p align="center">
  <a href="https://Cypher-Notepad.github.io/wiki/#/?coverpage=false"><img src="https://img.shields.io/badge/documentation-%20is%20ready-brightgreen?style=flat-square" alt="Documentation is ready" /></a>
  <br>
  A Java-based, plain-text (<code>.txt</code>) editor for file encryption.
  <br>
  <i>Easy, Secure, Simple, Free, and <b>Compatible with any OS</b></i>
</p>

<p align="center">
 <img src="https://img.shields.io/github/v/release/Cypher-Notepad/Cypher-Notepad?style=flat-square" alt="GitHub release (latest by date)" />
 <img src="https://img.shields.io/github/release-date/cypher-notepad/cypher-notepad?style=flat-square" alt="GitHub Release Date" />
 <img src="https://img.shields.io/github/downloads/cypher-notepad/cypher-notepad/total?color=%23ff69b4&style=flat-square" alt="GitHub All Releases" />
 <img src="https://img.shields.io/github/last-commit/cypher-notepad/cypher-notepad?style=flat-square" alt="GitHub last commit" />
 <a href="http://hits.dwyl.com/Cypher-Notepad/Cypher-Notepad"><img src="http://hits.dwyl.com/Cypher-Notepad/Cypher-Notepad.svg" alt="HitCount" /></a>
</p>


### To download, [See our documentation](https://cyphernotepad.com/wiki/#/installation).

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


### To see all the information of Cypher Notepad, [See our documentation](https://cyphernotepad.com/wiki/#/?coverpage=false).


