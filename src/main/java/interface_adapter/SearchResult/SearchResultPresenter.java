package interface_adapter.SearchResult;

import interface_adapter.weather.WeatherViewModel;
import use_case.note.search_result.SearchResultOutputBoundary;
import use_case.note.search_result.SearchResultOutputData;


public class SearchResultPresenter implements SearchResultOutputBoundary {

    private final SearchResultViewModel viewModel;

    public SearchResultPresenter(SearchResultViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void presentSuccessView(SearchResultOutputData searchResultOutputData) {
        viewModel.getState().setWeather(searchResultOutputData.getWeather());
        viewModel.getState().setCityName(searchResultOutputData.getWeather().getCityName());
    }

    @Override
    public void presentFailView(String errorMessage) {
        viewModel.getState().setError(errorMessage);
    }
}
