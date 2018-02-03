(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('OccupationController', OccupationController);

    OccupationController.$inject = ['Occupation', 'OccupationSearch'];

    function OccupationController(Occupation, OccupationSearch) {

        var vm = this;

        vm.occupations = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Occupation.query(function(result) {
                vm.occupations = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            OccupationSearch.query({query: vm.searchQuery}, function(result) {
                vm.occupations = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
