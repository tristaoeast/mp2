#!/bin/bash

#TODO: Mudar farses de input
rm -f Ex2.class
javac Ex2.java
java Ex2 irUnigramas.txt irBigramas.txt serUnigramas.txt serBigramas.txt foiParametrizacao.txt foiIrSer-1.out
java Ex2 verUnigramas.txt verBigramas.txt venderUnigramas.txt venderBigramas.txt vendoParametrizacao.txt vendoVenderVer.out