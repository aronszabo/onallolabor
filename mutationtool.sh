#!/bin/bash
# $1 = project PATH
# $2 = test class
BASEDIR=$(dirname $0)
# testnames=$(javap -cp "$1/target/test-classes" $2 | grep "public void" | sed -E "s/public void (.*)\(\);/\1/")
echo "WITHOUT MUTATION"
java -cp "$BASEDIR/lib/*:$1/target/test-classes:$1/target/classes" org.junit.runner.JUnitCore $2 | grep "Tests run\|OK"

testclass=$(echo $2 | sed -E "s/\./\//")
java -cp "$BASEDIR/target/classes:$BASEDIR/lib/*" hu.bme.mit.szaboaron.mutationtool.main.Main "$1/target/test-classes/$testclass.class"
# for tester in $testnames
# do
#    testclass=$(echo $2 | sed -E "s/\./\//")
#    echo "Mutating for test $tester"
#    java -cp "$BASEDIR/target/classes:$BASEDIR/lib/*" hu.bme.mit.szaboaron.mutationtool.main.Main "$1/target/test-classes/$testclass.class" $tester
#
#    # HACK mavennel a junit
#
# done
for mut in $(find $1 -regex '.*\.class\.OP[0-9]*\.mutation')
do
  realname=$(echo $mut | rev | cut -d "." -f3- | rev)
  mv $mut $realname

  sz=${#1}
  fc=$(echo $realname | rev | cut -d "." -f2- | rev)
  clsname=${fc:sz}
  clsname=$(echo $clsname | sed -E "s/\//\./g" | cut -d "." -f4- )

  echo "RUNNING $(basename $mut) [$clsname]"
  result=$(java -cp "$BASEDIR/lib/*:$1/target/test-classes:$1/target/classes" org.junit.runner.JUnitCore $2 | grep "Tests run\|OK")
  if echo $result | grep "OK"; then
    echo "0" >> "$1/target/arontest-reports/$clsname"
  else
    echo "1" >> "$1/target/arontest-reports/$clsname"
  fi
  rm $realname
done

for f in $(find $1 -iname '*.class.original')
do
  mv $f $(echo $f | rev | cut -d "." -f2- | rev)
 #echo "renamed $f"
done
