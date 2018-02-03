(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('DomicileController', DomicileController);

    DomicileController.$inject = ['Domicile', 'DomicileSearch'];

    function DomicileController(Domicile, DomicileSearch) {

        var vm = this;

        vm.domiciles = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Domicile.query(function(result) {
                vm.domiciles = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            DomicileSearch.query({query: vm.searchQuery}, function(result) {
                vm.domiciles = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
