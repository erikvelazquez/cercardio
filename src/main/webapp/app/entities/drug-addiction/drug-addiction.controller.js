(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('DrugAddictionController', DrugAddictionController);

    DrugAddictionController.$inject = ['DrugAddiction', 'DrugAddictionSearch'];

    function DrugAddictionController(DrugAddiction, DrugAddictionSearch) {

        var vm = this;

        vm.drugAddictions = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            DrugAddiction.query(function(result) {
                vm.drugAddictions = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            DrugAddictionSearch.query({query: vm.searchQuery}, function(result) {
                vm.drugAddictions = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
