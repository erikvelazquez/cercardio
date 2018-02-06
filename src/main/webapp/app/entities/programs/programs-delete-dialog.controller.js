(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('ProgramsDeleteController',ProgramsDeleteController);

    ProgramsDeleteController.$inject = ['$uibModalInstance', 'entity', 'Programs'];

    function ProgramsDeleteController($uibModalInstance, entity, Programs) {
        var vm = this;

        vm.programs = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Programs.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
