import subprocess
import sys
from io import StringIO
subprocess.check_call([sys.executable, '-m', 'pip', 'install',"biopython"])
# subprocess.check_call([sys.executable, '-m', 'pip', 'install',"matplotlib"])


from Bio import Phylo
import matplotlib.pyplot as plt

# Récupérer les arguments depuis l'entrée standard (stdin)
# print(str(sys.argv))
# print(sys.argv[1])

# Lire l'arbre à partir d'une séquence Newick
newick_string = str(sys.argv[1])
tree = Phylo.read(StringIO(newick_string), 'newick')
# Représenter l'arbre non enraciné
Phylo.draw(tree)