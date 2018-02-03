(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('PacientGeneralsController', PacientGeneralsController);

    PacientGeneralsController.$inject = ['PacientGenerals', 'PacientGeneralsSearch'];

    function PacientGeneralsController(PacientGenerals, PacientGeneralsSearch) {

        var vm = this;

        vm.pacientGenerals = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            PacientGenerals.query(function(result) {
                vm.pacientGenerals = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            PacientGeneralsSearch.query({query: vm.searchQuery}, function(result) {
                vm.pacientGenerals = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
