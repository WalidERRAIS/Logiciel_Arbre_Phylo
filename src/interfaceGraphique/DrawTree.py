from Bio import Phylo
from io import StringIO

import sys

# Récupérer les arguments depuis l'entrée standard (stdin)
# print(str(sys.argv))
# print(sys.argv[1])

newick_string_nj = str(sys.argv[1])
tree_nj = Phylo.read(StringIO(newick_string_nj), 'newick')
# Représenter l'arbre non enraciné
Phylo.draw(tree_nj)

# Lire l'arbre à partir d'une séquence Newick
newick_string_upgma = str(sys.argv[2])
tree_upgma = Phylo.read(StringIO(newick_string_upgma), 'newick')
# Représenter l'arbre non enraciné
Phylo.draw(tree_upgma)

