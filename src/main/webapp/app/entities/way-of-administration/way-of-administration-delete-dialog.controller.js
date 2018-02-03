(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('Way_of_AdministrationDeleteController',Way_of_AdministrationDeleteController);

    Way_of_AdministrationDeleteController.$inject = ['$uibModalInstance', 'entity', 'Way_of_Administration'];

    function Way_of_AdministrationDeleteController($uibModalInstance, entity, Way_of_Administration) {
        var vm = this;

        vm.way_of_Administration = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Way_of_Administration.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
