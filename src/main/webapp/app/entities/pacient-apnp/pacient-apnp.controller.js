(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('PacientApnpController', PacientApnpController);

    PacientApnpController.$inject = ['PacientApnp', 'PacientApnpSearch'];

    function PacientApnpController(PacientApnp, PacientApnpSearch) {

        var vm = this;

        vm.pacientApnps = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            PacientApnp.query(function(result) {
                vm.pacientApnps = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            PacientApnpSearch.query({query: vm.searchQuery}, function(result) {
                vm.pacientApnps = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
