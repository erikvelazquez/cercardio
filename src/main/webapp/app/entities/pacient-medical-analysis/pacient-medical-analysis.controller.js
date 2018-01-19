(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('PacientMedicalAnalysisController', PacientMedicalAnalysisController);

    PacientMedicalAnalysisController.$inject = ['PacientMedicalAnalysis', 'PacientMedicalAnalysisSearch'];

    function PacientMedicalAnalysisController(PacientMedicalAnalysis, PacientMedicalAnalysisSearch) {

        var vm = this;

        vm.pacientMedicalAnalyses = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            PacientMedicalAnalysis.query(function(result) {
                vm.pacientMedicalAnalyses = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            PacientMedicalAnalysisSearch.query({query: vm.searchQuery}, function(result) {
                vm.pacientMedicalAnalyses = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
