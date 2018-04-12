#!/bin/bash
# $1 = project PATH
# $2 = test class
BASEDIR=$(dirname $0)
testnames=$(javap -cp "$1/target/test-classes" $2 | grep "public void" | sed -E "s/public void (.*)\(\);/\1/")
for tester in $testnames
do
   testclass=$(echo $2 | sed -E "s/\./\//")
   echo "Mutating for test $tester"
   echo "WITHOUT MUTATION"
   java -cp "$BASEDIR/lib/*:$1/target/test-classes:$1/target/classes" org.junit.runner.JUnitCore $2 | grep "Tests run\|OK"
   java -cp "$BASEDIR/target/classes:$BASEDIR/lib/*" hu.bme.mit.szaboaron.mutationtool.main.Main "$1/target/test-classes/$testclass.class" $tester
   for mut in $(find $(pwd) -regex '.*\.class\.OP[0-9]*\.mutation')
   do
     realname=$(echo $mut | rev | cut -d "." -f3- | rev)
     mv $mut $realname
     echo "RUNNING $(basename $mut)"
     java -cp "$BASEDIR/lib/*:$1/target/test-classes:$1/target/classes" org.junit.runner.JUnitCore $2 | grep "Tests run\|OK"
     rm $realname
   done
   # HACK mavennel a junit
   for f in $(find $1 -iname '*.class.original')
   do
     mv $f $(echo $f | rev | cut -d "." -f2- | rev)
    #echo "renamed $f"
   done
done
