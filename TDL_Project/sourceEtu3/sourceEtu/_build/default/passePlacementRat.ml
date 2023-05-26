(* Module de la passe de gestion des types *)
(*module PassePlacementRat : Passe.Passe with type t1 = Ast.AstType.programme and type t2 = Ast.AstPlacement.programme =
struct

  open Tds
  open Exceptions
  open Ast
  open AstPlacement
  open Type

  type t1 = Ast.AstType.programme
  type t2 = Ast.AstPlacement.programme

(* Analyse le placement d'un instuction et renvoie la nouvelle instruction *)
let rec analyse_placement_instruction base reg i =
  (match i with
  | AstType.Declaration (info, _) ->    
            begin (match (info_ast_to_info info) with 
            | InfoVar(_,t,_,_,_)-> modifier_adresse_info base reg info; (Type.getTaille t)
            | InfoStruct(_,lp,_,_)-> let list_types_noms = List.map (fun (t,info) -> (match info_ast_to_info info with 
                  | InfoVar (n,_,_,_,_) -> (t,n) 
                  | InfoStruct (n,_,_,_) -> (t,n) 
                  | _ -> assert false )) lp in modifier_adresse_info base reg info; (Type.getTaille (TypeStruct list_types_noms))
            |_ -> assert false)
            end
  | AstType.Conditionnelle (_,t,e) -> 
            analyse_placement_bloc base reg t;
            analyse_placement_bloc base reg e;
            0
  | AstType.TantQue (_,b) -> 
      analyse_placement_bloc base reg b;
      0
  |AstType.ConditionnelleSimple(_,b) ->
      analyse_placement_bloc base reg b;
      0
  | _-> 0)

(* Analyse le placement d'un bloc et renvoie le nouveau bloc *)
and analyse_placement_bloc add reg blc =
match blc with 
| [] -> [],0
| i::q ->
  (let nins = analyser_dep_instruction add reg i in 
  let lstinstr,taille = (analyser_dep_bloc (add + gettaille(nins)) reg q) in 
  (nins::lstinstr),(taille+gettaille(nins)))



(* Analyse le placement d'une fonction et renvoie la nouvelle fonction *)
let analyse_placement_fonction (AstType.Fonction(n,lp,li,e)) = 
  let calcul param d =
    match info_ast_to_info param with
    | InfoVar(_,t,_,_) ->  
        let nd = d-getTaille t in 
        modifier_adresse_info nd "LB" param;
        nd
    | _ -> failwith ""
  in
  let _ = List.fold_right calcul lp 0 in
  let _ = analyse_placement_bloc li 3 "LB" in
  Fonction(n, lp, li, e)

  


(* Analyse le placement d'un programme et renvoie le nouveau programme *)

let rec analyser p = 
  (match p with 
  |AstType.Programme (defTypes,fonction,p1) ->  let nf = analyse_placement_fonction fonction in 
  let np = analyser p1 in
  Programme (defTypes,nf,np)
  | Ast.AstType.Bloc b -> 
      analyse_placement_bloc 0 "SB" b;
      Ast.AstPlacement.Bloc(b))
end *)