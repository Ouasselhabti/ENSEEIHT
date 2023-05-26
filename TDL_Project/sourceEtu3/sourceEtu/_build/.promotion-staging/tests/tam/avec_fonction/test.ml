open Rat
open Compilateur

(* Changer le chemin d'accès du jar. *)
let runtamcmde = "java -jar ../../../../../tests/runtam.jar"
(* let runtamcmde = "java -jar /mnt/n7fs/.../tools/runtam/runtam.jar" *)

(* Execute the TAM code obtained from the rat file and return the ouptut of this code *)
let runtamcode cmde ratfile =
  let tamcode = compiler ratfile in
  let (tamfile, chan) = Filename.open_temp_file "test" ".tam" in
  output_string chan tamcode;
  close_out chan;
  let ic = Unix.open_process_in (cmde ^ " " ^ tamfile) in
  let printed = input_line ic in
  close_in ic;
  Sys.remove tamfile;    (* à commenter si on veut étudier le code TAM. *)
  String.trim printed

(* Compile and run ratfile, then print its output *)
let runtam ratfile =
  print_string (runtamcode runtamcmde ratfile)

(****************************************)
(** Chemin d'accès aux fichiers de test *)
(****************************************)

let pathFichiersRat = "../../../../../tests/tam/avec_fonction/fichiersRat/"

(**********)
(*  TESTS *)
(**********)


(* requires ppx_expect in jbuild, and `opam install ppx_expect` *)
let%expect_test "testfun1" =
  runtam (pathFichiersRat^"testfun1.rat");
  [%expect{| Syntaxic error: asm.SyntaxicError: Error : Syntax error |}]

let%expect_test "testfun2" =
  runtam (pathFichiersRat^"testfun2.rat");
  [%expect{| Syntaxic error: asm.SyntaxicError: Error : Syntax error |}]

let%expect_test "testfun3" =
  runtam (pathFichiersRat^"testfun3.rat");
  [%expect{| Syntaxic error: asm.SyntaxicError: Error : Syntax error |}]

let%expect_test "testfun4" =
  runtam (pathFichiersRat^"testfun4.rat");
  [%expect{| Syntaxic error: asm.SyntaxicError: Error : Syntax error |}]

let%expect_test "testfun5" =
  runtam (pathFichiersRat^"testfun5.rat");
  [%expect{| Syntaxic error: asm.SyntaxicError: Error : Syntax error |}]

let%expect_test "testfun6" =
  runtam (pathFichiersRat^"testfun6.rat");
  [%expect.unreachable]
[@@expect.uncaught_exn {|
  (* CR expect_test_collector: This test expectation appears to contain a backtrace.
     This is strongly discouraged as backtraces are fragile.
     Please change this test to not include a backtrace. *)

  (Rat.Parser.MenhirBasics.Error)
  Raised at Rat__Parser.MenhirBasics._eRR in file "parser.ml" (inlined), line 8, characters 6-17
  Called from Rat__Parser._menhir_run_048 in file "parser.ml", line 1411, characters 10-17
  Called from Rat__Compilateur.compiler in file "compilateur.ml", line 93, characters 14-45
  Re-raised at Rat__Compilateur.compiler in file "compilateur.ml", line 101, characters 6-13
  Called from Avec_fonction_tam__Test.runtamcode in file "tests/tam/avec_fonction/test.ml", line 10, characters 16-32
  Called from Avec_fonction_tam__Test.runtam in file "tests/tam/avec_fonction/test.ml" (inlined), line 22, characters 15-46
  Called from Avec_fonction_tam__Test.(fun) in file "tests/tam/avec_fonction/test.ml", line 57, characters 2-41
  Called from Expect_test_collector.Make.Instance_io.exec in file "collector/expect_test_collector.ml", line 262, characters 12-19

  Trailing output
  ---------------
  File "../../../../../tests/tam/avec_fonction/fichiersRat/testfun6.rat", line 24, characters 32-33: syntax error. |}]

let%expect_test "testfuns" =
  runtam (pathFichiersRat^"testfuns.rat");
  [%expect{| Syntaxic error: asm.SyntaxicError: Error : Syntax error |}]

let%expect_test "factrec" =
  runtam (pathFichiersRat^"factrec.rat");
  [%expect{| Syntaxic error: asm.SyntaxicError: Error : Syntax error |}]


