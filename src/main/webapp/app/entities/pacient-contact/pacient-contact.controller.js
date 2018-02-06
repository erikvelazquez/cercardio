(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('PacientContactController', PacientContactController);

    PacientContactController.$inject = ['PacientContact', 'PacientContactSearch'];

    function PacientContactController(PacientContact, PacientContactSearch) {

        var vm = this;

        vm.pacientContacts = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            PacientContact.query(function(result) {
                vm.pacientContacts = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            PacientContactSearch.query({query: vm.searchQuery}, function(result) {
                vm.pacientContacts = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
