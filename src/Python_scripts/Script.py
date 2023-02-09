import Bio.Phylo as Phylo
from io import StringIO


tree = Phylo.read(StringIO("(A, (B, C), (D, E))"), "newick")
print(Phylo.draw_ascii(tree))
