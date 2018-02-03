(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('MedicalAnalysisController', MedicalAnalysisController);

    MedicalAnalysisController.$inject = ['MedicalAnalysis', 'MedicalAnalysisSearch'];

    function MedicalAnalysisController(MedicalAnalysis, MedicalAnalysisSearch) {

        var vm = this;

        vm.medicalAnalyses = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            MedicalAnalysis.query(function(result) {
                vm.medicalAnalyses = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            MedicalAnalysisSearch.query({query: vm.searchQuery}, function(result) {
                vm.medicalAnalyses = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
