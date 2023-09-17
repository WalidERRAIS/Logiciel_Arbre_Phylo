def read_fasta(file_path):
    sequences = []
    with open(file_path, 'r') as file:
        lines = file.readlines()
        current_sequence = None
        for line in lines:
            if line.startswith('>'):
                if current_sequence:
                    sequences.append(current_sequence)
                current_sequence = {'header': line.strip(), 'sequence': ''}
            else:
                current_sequence['sequence'] += line.strip()
        if current_sequence:
            sequences.append(current_sequence)
    return sequences

    
def main(input_file, start, end, output_file):
    sequences = read_fasta(input_file)
    print(sequences)
    
    with open(output_file, 'w') as out_file:
        for sequence in sequences:
            header = sequence['header'][1:]
            sequence_data = sequence['sequence']
            truncated_sequence = sequence_data[start - 1:end]
            out_file.write(f">{start}-{end} {header}\n{truncated_sequence}\n")

if __name__ == "__main__":
    input_file = r"C:\Users\pietr\Documents\Resultats\all_seq\env_prot_all.fasta"
    start = 1  #début du tronçon
    end = 100  #fin du tronçon
    output_file = r"C:\Users\pietr\Documents\Resultats\all_seq\env_prot_1-100_all.fasta"
    main(input_file, start, end, output_file)