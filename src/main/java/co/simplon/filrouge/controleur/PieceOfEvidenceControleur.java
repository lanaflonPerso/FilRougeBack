package co.simplon.filrouge.controleur;


import co.simplon.filrouge.modele.PieceOfEvidence;
import co.simplon.filrouge.repository.PieceOfEvidenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class PieceOfEvidenceControleur {

    @Autowired
    private PieceOfEvidenceRepository pieceOfEvidenceRepository;

    /**
     * @return
     * Consulter toutes les PieceOfEvidence
     */
    @GetMapping("/pieceofevidence")
    public List<PieceOfEvidence> getAllPiece() {
        return pieceOfEvidenceRepository.findAll();
    }

    /**
     * @param pieceOfEvidence
     * @return
     * Creer une nouvelle PieceOfEvidence
     */
    @PostMapping("/pieceofevidence")
    public PieceOfEvidence createPiece(@Valid @RequestBody PieceOfEvidence pieceOfEvidence) {
        return pieceOfEvidenceRepository.save ( pieceOfEvidence );
    }

    /**
     * @param pieceId
     * @return
     * Consulter une seule PieceOfEvidence
     */
    @GetMapping("/pieceofevidence/{id}")
    public ResponseEntity<PieceOfEvidence> getPieceById(@PathVariable(value = "id") Long pieceId) {
        PieceOfEvidence pieceOfEvidence = pieceOfEvidenceRepository.findOne (pieceId);
        if(pieceOfEvidence == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(pieceOfEvidence);
    }

    /**
     * @param pieceId
     * @param pieceDetail
     * @return
     * Mise à jour d'une PieceOfEvidence
     */
    @PutMapping("/pieceofevidence/{id}")
    public ResponseEntity<PieceOfEvidence> updatePiece(@PathVariable(value = "id") Long pieceId,
                                                  @Valid @RequestBody PieceOfEvidence pieceDetail) {
        PieceOfEvidence pieceOfEvidence = pieceOfEvidenceRepository.findOne(pieceId);
        if(pieceOfEvidence == null) {
            return ResponseEntity.notFound().build();
        }
        pieceOfEvidence.setType (pieceDetail.getType ());
        pieceOfEvidence.setSerialNumber (pieceDetail.getSerialNumber ());

        PieceOfEvidence updatedPiece = pieceOfEvidenceRepository.save(pieceOfEvidence);
        return ResponseEntity.ok(updatedPiece);
    }


    /**
     * @param pieceId
     * @return
     * Efface une PieceOfEvidence
     */
    @DeleteMapping("/pieceofevidence/{id}")
    public ResponseEntity<PieceOfEvidence> deletePiece(@PathVariable(value = "id") Long pieceId) {
        PieceOfEvidence pieceOfEvidence = pieceOfEvidenceRepository.findOne(pieceId);
        if(pieceOfEvidence == null) {
            return ResponseEntity.notFound().build();
        }

        pieceOfEvidenceRepository.delete(pieceOfEvidence);
        return ResponseEntity.ok().build();
    }
}