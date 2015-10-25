#!/bin/bash

chmod u+x clean.sh
./clean.sh

rm -f Ex1B.class
javac Ex1B.java
java Ex1B foiIrSer-1.out
java Ex1B vendoVenderVer.out


rm -f Ex2.class
javac Ex2.java
java Ex2 irUnigramas.txt irBigramas.txt serUnigramas.txt serBigramas.txt foiParametrizacao.txt foiFrases.txt
java Ex2 verUnigramas.txt verBigramas.txt venderUnigramas.txt venderBigramas.txt vendoParametrizacao.txt vendoFrases.txt

