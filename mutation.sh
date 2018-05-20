#!/bin/bash
# $1 = project PATH
rm -rf "$1/target/arontest-reports"
mkdir "$1/target/arontest-reports"
for testclass in $( cd $1 && find target/test-classes -iname "*.class" | cut -d "/" -f3- | rev | cut -d "." -f2- | rev | sed -E "s/\//\./g"  )
do

  cd $(dirname $0) && ./mutationtool.sh $1 $testclass
done
for testresult in $( cd $1/target/arontest-reports && ls )
do
  all=$(cd $1/target/arontest-reports && cat $testresult | wc -l | awk '{$1=$1};1')
  killed=$(cd $1/target/arontest-reports && cat $testresult | grep "1" | wc -l | awk '{$1=$1};1')
  echo "$testresult: $killed/$all"
done
