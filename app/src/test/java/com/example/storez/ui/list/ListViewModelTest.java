package com.example.storez.ui.list;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

import android.os.Build;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.storez.models.ListPreferenceHelper;
import com.example.storez.ui.list.ListViewModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.O_MR1})
public class ListViewModelTest {
    @Mock
    private Observer<Double> budgetObserver;
    @Mock
    private ListPreferenceHelper preferenceHelper;

    private ListViewModel listViewModel;

    @Before
    public void setup() {
        ShadowLog.stream = System.out; // Print logs to the console

        MockitoAnnotations.initMocks(this);
        listViewModel = new ListViewModel(preferenceHelper);
        listViewModel.getBudget().observeForever(budgetObserver);
    }

    @Test
    public void setBudget_updatesBudgetLiveData() {
        // Arrange
        MutableLiveData<Double> budgetLiveData = (MutableLiveData<Double>) listViewModel.getBudget();
        double newBudget = 100.0;

        // Act
        listViewModel.setBudget(newBudget);

        // Assert
        verify(budgetObserver).onChanged(newBudget);
        assertEquals(newBudget, budgetLiveData.getValue(), 0.0);
    }
}