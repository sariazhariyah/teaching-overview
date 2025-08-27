<?php
$x = 5;
$y = 10;
var_dump($x != $y);
echo "<br>";

$x = 10;
$y = 10;
var_dump($x !== $y);
echo "<br>";

$x = 16;
$y = 10;
var_dump($x == $y);
echo "<br>";

$x = 6;
$y = 6;
var_dump($x === $y);
echo "<br>";

$x = 15;
$y = 10;
echo ($x <=> $y);
