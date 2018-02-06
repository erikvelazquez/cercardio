(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('ReligionController', ReligionController);

    ReligionController.$inject = ['Religion', 'ReligionSearch'];

    function ReligionController(Religion, ReligionSearch) {

        var vm = this;

        vm.religions = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Religion.query(function(result) {
                vm.religions = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            ReligionSearch.query({query: vm.searchQuery}, function(result) {
                vm.religions = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
