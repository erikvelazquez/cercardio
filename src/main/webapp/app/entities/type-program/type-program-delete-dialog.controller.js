(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('Type_ProgramDeleteController',Type_ProgramDeleteController);

    Type_ProgramDeleteController.$inject = ['$uibModalInstance', 'entity', 'Type_Program'];

    function Type_ProgramDeleteController($uibModalInstance, entity, Type_Program) {
        var vm = this;

        vm.type_Program = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Type_Program.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
