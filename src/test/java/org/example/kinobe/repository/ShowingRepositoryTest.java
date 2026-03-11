package org.example.kinobe.repository;

import org.example.kinobe.misc.Status;
import org.example.kinobe.model.Showing;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ShowingRepositoryTest {

    @Autowired
    private ShowingRepository showingRepository;

    @Test
    public void showingRepository_SaveShowingToDB_GetShowingId(){
        Showing showing = Showing.builder().date(LocalDate.now()).time(LocalTime.now()).status(Status.ACTIVE).build();
        showingRepository.save(showing);

        assertThat(showing.getShowingId()).isNotNull();
    }

    @Test
    public void showingRepository_UpdateNewShowingInDB_ConfirmChanges(){
        Showing showing = Showing.builder().date(LocalDate.now()).time(LocalTime.now()).status(Status.ACTIVE).build();
        showingRepository.save(showing);

        showing.setStatus(Status.CANCELLED);
        showingRepository.save(showing);
        Showing saved = showingRepository.getReferenceById(showing.getShowingId());

        assertThat(saved).isNotNull();
        assertThat(showingRepository.count() == 2);
        assertThat(saved.getStatus()).isEqualTo(showing.getStatus());
    }

    @Test
    public void showingRepository_GetAllShowingsFromDB_ReturnListWithAllShowings(){
        for(int i = 0; i<10; i++){
            Showing showing = new Showing();
            showingRepository.save(showing);
        }

        List<Showing> showingList = showingRepository.findAll();
        long showingTableSize = showingRepository.count();

        assertThat(showingList.size() == showingTableSize);
    }

    @Test
    public void showingRepository_DeleteShowingFromDB_ReturnNoShowingFoundById(){
        long showingTableSize = showingRepository.count();
        Showing showing = new Showing();
        showingRepository.save(showing);

        assertThat(showingRepository.count() == (showingTableSize + 1));

        showingTableSize = showingRepository.count();
        showingRepository.deleteById(showing.getShowingId());

        assertThat(showingRepository.count() == (showingTableSize - 1));
    }
}